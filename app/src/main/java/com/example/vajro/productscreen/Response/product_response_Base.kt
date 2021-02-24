import com.google.gson.annotations.SerializedName

data class product_response_Base (

	@SerializedName("products") val products : List<Products>
)