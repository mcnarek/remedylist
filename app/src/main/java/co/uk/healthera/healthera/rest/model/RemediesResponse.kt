package co.uk.healthera.healthera.rest.model

import com.google.gson.annotations.SerializedName

data class RemediesResponse(
	@field:SerializedName("data")
	val data: List<RemedyData>? = null
)

data class Medicine(
	@field:SerializedName("discontinued_date")
	val discontinuedDate: String? = null,

	@field:SerializedName("prescription_charges")
	val prescriptionCharges: Int = 0,

	@field:SerializedName("medicine_id")
	val medicineId: String? = null,

	@field:SerializedName("generic_name")
	val genericName: String? = null,

	@field:SerializedName("nhs_price")
	val nhsPrice: Int = 0,

	@field:SerializedName("course_quantity")
	val courseQuantity: Int = 0,

	@field:SerializedName("ampp_name")
	val amppName: String? = null,

	@field:SerializedName("amp_id")
	val ampId: String? = null,

	@field:SerializedName("medicine_name")
	val medicineName: String? = null,

	@field:SerializedName("pip_code")
	val pipCode: Any? = null,

	@field:SerializedName("controlled")
	val controlled: Boolean = false,

	@field:SerializedName("ampp_id")
	val amppId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("vmpp_id")
	val vmppId: String? = null,

	@field:SerializedName("nhs_price_date")
	val nhsPriceDate: String? = null,

	@field:SerializedName("gtin")
	val gtin: List<String?>? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)

data class ScheduleItem(
	@field:SerializedName("dose_max")
	val doseMax: Int = 0,

	@field:SerializedName("dose_min")
	val doseMin: Int = 0,

	@field:SerializedName("day_iterator")
	val dayIterator: Int = 0,

	@field:SerializedName("day_offset")
	val dayOffset: Any? = null,

	@field:SerializedName("alarm_window")
	val alarmWindow: Int = 0
)

data class RemedyData(
	@field:SerializedName("end_date")
	val endDate: Long = 0,

	@field:SerializedName("medicine_type")
	val medicineType: String? = null,

	@field:SerializedName("allow_edit")
	val allowEdit: Boolean = false,

	@field:SerializedName("medicine_id")
	val medicineId: String? = null,

	@field:SerializedName("date_created")
	val dateCreated: Long = 0,

	@field:SerializedName("repeat_cycle")
	val repeatCycle: Int = 0,

	@field:SerializedName("medicine")
	val medicine: Medicine? = null,

	@field:SerializedName("can_request")
	val canRequest: Boolean = false,

	@field:SerializedName("medicine_name")
	val medicineName: String? = null,

	@field:SerializedName("reorder_timestamp")
	val reorderTimestamp: Long = 0,

	@field:SerializedName("remedy_id")
	val remedyId: String? = null,

	@field:SerializedName("schedule")
	val schedule: List<Any?>? = null,

	@field:SerializedName("is_ongoing")
	val isOngoing: Boolean = false,

	@field:SerializedName("date_modified")
	val dateModified: Long = 0,

	@field:SerializedName("patient_id")
	val patientId: String? = null,

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("special_instruction")
	val specialInstruction: Any? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("is_current")
	val isCurrent: Boolean = false,

	@field:SerializedName("start_date")
	val startDate: Long = 0
)
