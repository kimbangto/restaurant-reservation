package controller

class RestaurantManagementController {
    private val customerManagementController = CustomerManagementController()
    private val salesManagementController = SalesManagementController()
    private val orderPayManagementController = OrderPayManagementController()
    private val menuManagementController = MenuManagementController()

    fun startRestaurantManagement() {
        while (true) {
            println("1. 고객관리 | 2. 매출관리 | 3. 주문/결제 | 4. 메뉴관리 | 0. 돌아가기")

            val input = readln()

            when (input) {
                "1" -> {
                    customerManagementController.startCustomerManagement()
                }
                "2" -> {
                    salesManagementController.startSalesManagement()
                }
                "3" -> {
                    orderPayManagementController.startOderPay()
                }
                "4" -> {
                    menuManagementController.startMenuManagement()
                }
                "0" -> {
                    return
                }
                else -> {}
            }
        }
        }

}