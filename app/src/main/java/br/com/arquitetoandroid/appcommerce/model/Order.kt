package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "orders")
data class Order (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var time: Long,
    var status: Status,
    var method: Method,
    var userId: String,
    var price: Double = 0.0) : Serializable {

    @Ignore constructor(): this(UUID.randomUUID().toString(), Date().time, Status.CART, Method.NONE, "", 0.0)

    enum class Status(val message: String) {
        PENDENT("Pendente"),
        PAID("Pago"),
        PROCESSED("Enviado"),
        CART("Carrinho Abandonado")
    }

    enum class Method(val message: String) {
        CREDIT_CARD("Cartão de Crédito"),
        BOLETO("Boleto"),
        NONE("Nenhum")
    }

}