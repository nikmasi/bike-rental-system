package project.data

import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import project.data.models.Bike
import project.data.models.BikeReturn
import project.data.models.Rental
import project.data.models.User

private const val USERS_KEY = "users"

object Storage {
    private val users = loadUsers().toMutableList()
    private val bikes = loadBikes().toMutableList()
    private val rentals = loadRentals().toMutableList()
    private val returns = loadReturns().toMutableList()

    init {
        if (users.isEmpty()) {
            registerUser("koviljka", "koviljka123", "Koviljka Stanic", false)
            registerUser("stanislav", "stanislav123", "Stanislav Ostojic", false)
            registerUser("admin", "admin123", "Admin", false)
        } else if (users.none { it.username == "admin" }) {
            registerUser("admin", "admin123", "Admin", false)
        }

        if (bikes.isEmpty()) {
            bikes.add(Bike(1, "Classic", "Block 45, New Belgrade", 100.00, "/bike1.webp", status = "Problem"))
            bikes.add(Bike(2, "Classic", "Block 70, New Belgrade", 100.00, "/bike2.webp", status = "Problem"))
            bikes.add(Bike(3, "Electric", "Block 45, New Belgrade", 80.00, "bike3.webp"))
            bikes.add(Bike(4, "Electric", "Block 45, New Belgrade", 80.00, "bike3.webp"))
            bikes.add(Bike(5, "Electric", "Block 45, New Belgrade", 80.00, "bike4.webp"))
            bikes.add(Bike(6, "Electric", "Block 45, New Belgrade", 80.00, "bike3.webp"))
            bikes.add(Bike(7, "Electric", "Block 45, New Belgrade", 80.00, "bike4.webp"))
            saveBikes(bikes)
        }
        if (rentals.isEmpty()){
            rentals.add(Rental(1, "2026-01-18T10:00:00Z", "2026-01-18T12:00:00Z", 400.00, bikes[2], users[0]))
            rentals.add(Rental(2, "2026-01-11T10:00:00Z", "2026-01-11T12:00:00Z", 300.00, bikes[1], users[1]))
            rentals.add(Rental(3, "2026-01-19T10:00:00Z", "2026-01-19T12:00:00Z", 300.00, bikes[0], users[1]))
            saveRentals(rentals)
        }
        if (returns.isEmpty()){
            returns.add(BikeReturn(1, 1, 1768751333786, listOf("/bike1.webp"), false, null))
            returns.add(BikeReturn(2, 2, 1768751333786, listOf("/bike2.webp"), true, "The chain came off."))
            returns.add(BikeReturn(3, 3, 1768751333786, listOf("/bike3.webp"), true, "The pedal fell off."))
            saveReturns(returns)
        }
    }

    private fun saveUsers(users: List<User>) {
        val json = Json.encodeToString(users)
        window.localStorage.setItem(USERS_KEY, json)
    }

    private fun loadUsers(): List<User> {
        val json = window.localStorage.getItem(USERS_KEY)
        return if (json != null) {
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }

    private fun loadBikes(): List<Bike> {
        val json = window.localStorage.getItem("bikes")
        return if (json != null) {
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }

    private fun loadRentals(): List<Rental> {
        val json = window.localStorage.getItem("rentals")
        return if (json != null) {
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }

    private fun loadReturns(): List<BikeReturn> {
        val json = window.localStorage.getItem("returns")
        return if (json != null) {
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }

    fun registerUser(username: String, password: String, name: String, loggedIn: Boolean) {
        users.add(User(username, password, name, loggedIn))
        saveUsers(users)
    }

    fun changePasswordAdmin(password: String, oldPassword: String):ChangePasswordResult{
        val users = loadUsers().toMutableList()
        val u =users.find { it.username == "admin" && it.password == oldPassword }
        if (u!= null){
            u?.password = password
            saveUsers(users)
            return ChangePasswordResult.Success
        }
        else{
            return ChangePasswordResult.WrongPassword
        }
    }

    fun registerBike(bike: Bike): NewBikeResult {

        if (bike.location.isBlank()) {
            return NewBikeResult.EmptyLocation
        }

        if (bike.price <= 0) {
            return NewBikeResult.InvalidPrice
        }

        if (bike.photo.isBlank()) {
            return NewBikeResult.MissingPhoto
        }

        bikes.add(bike)
        saveBikes(bikes)

        return NewBikeResult.Success
    }


    private fun saveBikes(bikes: List<Bike>) {
        val json = Json.encodeToString(bikes)
        window.localStorage.setItem("bikes", json)
    }

    fun updateBikeStatusAfterReturn(returnId: Int, status: String): Boolean {
        val bikeReturnIndex = returns.indexOfFirst { it.id == returnId }
        if (bikeReturnIndex == -1) return false

        val bikeReturn = returns.find { it.id == returnId } ?: return false
        val rental = rentals.find { it.id == bikeReturn.rentalId }
            ?: return false
        val bikeIndex = bikes.indexOfFirst { it.id == rental.bike.id }
        if (bikeIndex == -1) return false

//        val newStatus = if (bikeReturn.hasIssue) {
//            status
//        } else {
//            "Available"
//        }
        val newStatus = status

        val updatedBike = bikes[bikeIndex].copy(status = newStatus)
        bikes[bikeIndex] = updatedBike
        saveBikes(bikes)

        returns.removeAt(bikeReturnIndex)
        saveReturns(returns)

        return true
    }

    private fun saveRentals(rentals: List<Rental>) {
        val json = Json.encodeToString(rentals)
        window.localStorage.setItem("rentals", json)
    }

    private fun saveReturns(returns: List<BikeReturn>) {
        val json = Json.encodeToString(returns)
        window.localStorage.setItem("returns", json)
    }

    fun isLoggedIn(): Boolean {
        val user =window.localStorage.getItem("loggedIn")
        return user != null
    }

    fun login(username: String, password: String): LoginResult {
        if (username.isBlank()) {
            return LoginResult.UsernameEmpty
        }

        if (password.isBlank()) {
            return LoginResult.PasswordEmpty
        }

        if (username != "admin"){
            return LoginResult.UserNotFound
        }

        val user = users.find { it.username == username }

        val json = Json.encodeToString(user)
        window.localStorage.setItem("loggedIn", json)

        return when {
            user == null -> LoginResult.UserNotFound
            user.password != password -> LoginResult.WrongPassword
            else -> LoginResult.Success
        }
    }

    fun getAllUsers(): List<User> = users.toList()
    fun getAllBikes(): List<Bike> = bikes.toList()
    fun getAllRentals(): List<Rental> = rentals.toList()

    fun getAllReturns(): List<BikeReturn> = returns.toList()

    fun getAllProblems(): List<BikeReturn> = returns.filter { it.hasIssue }.toList()

    fun newIdBike(): Int = (bikes.maxOfOrNull { it.id } ?: 0) + 1

    fun deleteBike(id: Int){
        val bike = bikes.find { it.id == id} ?: return
        bikes.remove(bike)
        saveBikes(bikes)
    }

    fun editBike(id: Int, lok: String, cena: Double, photo: String, tip: String): NewBikeResult {
        val index = bikes.indexOfFirst { it.id == id }
        if (index == -1) return NewBikeResult.EmptyLocation

        if (lok.isBlank()) return NewBikeResult.EmptyLocation
        if (cena <= 0) return NewBikeResult.InvalidPrice
        if (photo.isBlank()) return NewBikeResult.MissingPhoto

        val updatedBike = bikes[index].copy(location = lok, price = cena, photo = photo, type = tip)

        bikes[index] = updatedBike

        saveBikes(bikes)

        return NewBikeResult.Success
    }

    fun editBikeStatus(id:Int, status: String): NewBikeResult{
        val index = bikes.indexOfFirst { it.id == id }
        if (index == -1) return NewBikeResult.EmptyLocation

        val updatedBike = bikes[index].copy(status=status)

        bikes[index] = updatedBike

        saveBikes(bikes)

        return NewBikeResult.Success
    }
}