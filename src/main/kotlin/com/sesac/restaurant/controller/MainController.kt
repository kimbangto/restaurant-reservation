package controller

import view.MainMenuView
import view.promptForInput
import view.ReservationView

class MainController(private val reservationManagementController: ReservationManagementController = ReservationManagementController(),
                     private val restaurantManagementController: RestaurantManagementController = RestaurantManagementController(),
                     private val view: MainMenuView = MainMenuView()) {

    /** main 화면 */
    suspend fun startProgram() {
        while (true) {
            when (promptForInput(view.mainMenu)) {
                view.select_reservationManagement -> reservationManagementController.reservationManagement()
                view.select_restaurantManagement -> restaurantManagementController.startRestaurantManagement()
                view.select_exit -> break
                else -> println("잘못된 입력입니다")
            }
        }
    }
}