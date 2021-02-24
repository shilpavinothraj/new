import com.google.gson.annotations.SerializedName

data class Products (

	@SerializedName("name") val name : String,
	@SerializedName("id") val id : String,
	@SerializedName("product_id") val product_id : String,
	@SerializedName("sku") val sku : String,
	@SerializedName("image") val image : String,
	@SerializedName("thumb") val thumb : String,
	@SerializedName("zoom_thumb") val zoom_thumb : String,
	@SerializedName("options") val options : List<String>,
	@SerializedName("description") val description : String,
	@SerializedName("href") val href : String,
	@SerializedName("quantity") val quantity : String,
	@SerializedName("images") val images : List<String>,
	@SerializedName("price") val price : String,
	@SerializedName("special") val special : String
)