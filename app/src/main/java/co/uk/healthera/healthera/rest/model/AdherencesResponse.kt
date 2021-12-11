package co.uk.healthera.healthera.rest.model

import com.google.gson.annotations.SerializedName

data class AdherencesResponse(
	@field:SerializedName("data")
	val data: List<AdherenceData>? = null
)

data class AdherenceData(
	@field:SerializedName("remedy_id")
	val remedyId: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("dose_discrete")
	val doseDiscrete: String? = null,

	@field:SerializedName("adherence_id")
	val adherenceId: String? = null,

	@field:SerializedName("action_time")
	val actionTime: Long? = null,

	@field:SerializedName("dose_quantity")
	val doseQuantity: Int = 0,

	@field:SerializedName("patient_id")
	val patientId: String? = null,

	@field:SerializedName("alarm_time")
	val alarmTime: Long = 0,

	@field:SerializedName("action")
	val action: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
