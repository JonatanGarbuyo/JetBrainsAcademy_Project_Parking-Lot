class Car(val plate: String = "", val color: String = "")

class Spot() {
    var spotIsEmpty = true
    var car: Car = Car()

    fun isEmpty(): Boolean = spotIsEmpty
    fun isNotEmpty(): Boolean = !spotIsEmpty

    fun removeCar() {
        car = Car()
        spotIsEmpty = true
    }

    fun park(newCar: Car) {
        car = newCar
        spotIsEmpty = false
    }
    override fun toString() = "${car.plate} ${car.color}"
}

class Parking() {
    var spots = mutableListOf<Spot>()

    fun createSpots(amount: Int) {
        spots = MutableList(amount) { Spot() }
        println("Created a parking lot with $amount spots.")
    }

    fun leave(index: Int) {
        if (spots[index].isEmpty()) {
            println("There is no car in spot ${index + 1}.")
        } else {
            spots[index].removeCar()
            println("Spot ${index + 1} is free.")
        }
    }

    fun parkCar(plate: String, color: String) {
        val car = Car(plate, color)
        val freeSpot = spots.indexOfFirst { it.isEmpty() }
        if (freeSpot < 0) {
            println("Sorry, the parking lot is full.")
            return
        }
        spots[freeSpot].park(car)
        println("${car.color} car parked in spot ${freeSpot + 1}.")
    }

    fun status() {
        if (spots.all { it.isEmpty() }) {
            println("Parking lot is empty.")
        }
        spots.forEachIndexed { index, spot ->
            if (spot.isNotEmpty()) println("${index + 1} $spot")
        }
    }

    fun regByColor(color: String) {
        val plates = mutableListOf<String>()

        spots.forEach { spot ->
            if (spot.isNotEmpty() && spot.car.color.lowercase() == color.lowercase()) plates.add(spot.car.plate)
        }
        if (plates.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            println(plates.joinToString(", "))
        }
    }

    fun spotByColor(color: String) {
        var spotsNumbers = mutableListOf<Int>()

        spots.forEachIndexed {index, spot ->
            if(spot.car.color.lowercase() == color.lowercase()) {
                spotsNumbers.add(index + 1)
            }
        }
        if (spotsNumbers.isEmpty()) {
            println("No cars with color $color were found.")
        } else {
            println(spotsNumbers.joinToString(", "))
        }

    }

    fun spotByRegistration(registrationNumber: String) {
        val spotNumber = spots.indexOfFirst {spot -> spot.car.plate == registrationNumber }
        if (spotNumber < 0) {
            println("No cars with registration number $registrationNumber were found.")
        } else {
            println(spotNumber + 1)
        }
    }
}


fun main() {
    var parking = Parking()

    fun isParkingLotCreated(): Boolean{
        if (parking.spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return false
        }
        return true
    }

    while (true) {
        val input: MutableList<String> = readLine()!!.split(" ").toMutableList()
        val command = input.removeFirst()

        when (command) {
            "create" -> {
                val (size) = input
                parking.createSpots(size.toInt())
            }
            "leave" -> {
                if (!isParkingLotCreated()) continue
                val (spotNum) = input
                val index = spotNum.toInt() - 1
                parking.leave(index)
            }
            "park" -> {
                if (!isParkingLotCreated()) continue
                val (plate, color) = input
                parking.parkCar(plate, color)
            }
            "status" -> {
                if (!isParkingLotCreated()) continue
                parking.status()
            }
            "reg_by_color" -> {
                if (!isParkingLotCreated()) continue
                val (color) = input
                parking.regByColor(color)
            }
            "spot_by_color" -> {
                if (!isParkingLotCreated()) continue
                val (color) = input
                parking.spotByColor(color)
            }
            "spot_by_reg" -> {
                if (!isParkingLotCreated()) continue
                val (registrationNumber) = input
                parking.spotByRegistration(registrationNumber)
            }
            "exit" -> {
                break
            }
        }
    }
}
