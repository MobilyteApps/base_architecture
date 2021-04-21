package com.baseappname.app.data.remote


import com.baseappname.app.data.model.request.SignUpRequest
import com.baseappname.app.data.model.response.*
import io.reactivex.Single
import retrofit2.http.*


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
interface APICallMethods {
    /*

        @GET("mobile/logout/")
        fun logout(
            @Header("Authorization") token: String, @Query("patient_id") patientId: Int
        ): Single<LogoutResponse>

        @POST("change/password")
        fun changePassword(
            @Header("Authorization") token: String,
            @Body changePasswordRequest: ChangePasswordRequest
        ): Single<ChangePasswordResponse>

        @GET("mobile/patients/states")
        fun getStates(@Header("Authorization") token: String): Single<StateListResponse>

        @Multipart
        @PUT("mobile/patients/{patientId}")
        fun updatePatientProfile(
            @Header("Authorization") token: String,
            @Path("patientId") userId: Int,
            @Part("first_name") firstName: RequestBody?,
            @Part("last_name") lastName: RequestBody?,
            @Part("email") email: RequestBody?,
            @Part("mobile") mobile: RequestBody?,
            @Part("address_line1") addressLine1: RequestBody?,
            @Part("address_line2") addressLine2: RequestBody?,
            @Part("city") city: RequestBody?,
            @Part("state") state: RequestBody?,
            @Part("country") country: RequestBody?,
            @Part("postal_code") postalCode: RequestBody?,
            @Part("dob") dob: RequestBody?,
            @Part("gender") gender: RequestBody?,
            @Part("smoking_status") smokingStatus: RequestBody?,
            @Part("marital_status") maritalStatus: RequestBody?,
            @Part("employment_status") employmentStatus: RequestBody?,
            @Part("blood_group") bloodGroup: RequestBody?,
            @Part profilePic: MultipartBody.Part?
        ): Single<UpdateProfileResponse>

        @POST("mobile/appointments/{patientId}")
        fun getAppointment(
            @Header("Authorization") token: String,
            @Path("patientId") userId: Int,
            @Body appointmentRequest: AppointmentRequest
        ): Single<AppointmentResponse>

        @GET("mobile/appointment/details/{appointmentId}")
        fun getAppointmentDetail(
            @Header("Authorization") token: String,
            @Path("appointmentId") appointmentId: Int
        ): Single<AppointmentDetailResponse>

        @GET("mobile/services")
        fun getServices(
            @Header("Authorization") token: String,
            @Query("client_id") clientId: Int,
            @Query("sort_column") sortingKey: String,
            @Query("sort_order") sortOrder: String,
            @Query("page") page: Int,
            @Query("per_page") count: Int,
            @Query("type") type: Int
        ): Single<ServiceResponse>

        @GET("mobile/medications/list")
        fun getAllMedication(@Header("Authorization") token: String): Single<MedicationListResponse>

        @GET("mobile/medications")
        fun getMedicationByPatient(
            @Header("Authorization") token: String,
            @Query("patient_id") userId: Int,
            @Query("sort_column") sortingKey: String,
            @Query("per_page") count: Int,
            @Query("page") pageNo: Int
        ): Single<MedicationByPatientResponse>

        @POST("mobile/medications")
        fun addMedication(
            @Header("Authorization") token: String,
            @Body medicationAddRequest: MedicationAddRequest
        ): Single<MedicationAddResponse>

        @DELETE("mobile/medications/{medicationId}")
        fun deleteMedication(
            @Header("Authorization") token: String,
            @Path("medicationId") medicationId: Int
        ): Single<MedicationDeleteResponse>

        @GET("mobile/alergies/list")
        fun getAllAllergies(@Header("Authorization") token: String): Single<AllergiesListResponse>

        @GET("mobile/alergies")
        fun getAllergiesByPatient(
            @Header("Authorization") token: String,
            @Query("patient_id") userId: Int,
            @Query("sort_column") sortingKey: String,
            @Query("per_page") count: Int,
            @Query("page") pageNo: Int
        ): Single<AllergiesByPatientResponse>

        @POST("mobile/alergies")
        fun addAlergies(
            @Header("Authorization") token: String,
            @Body allergyAddRequest: AllergyAddRequest
        ): Single<AllergyAddRespponse>

        @DELETE("mobile/alergies/{allergyId}")
        fun deleteAllergy(
            @Header("Authorization") token: String,
            @Path("allergyId") allergyId: Int
        ): Single<AllergyDeleteResponse>

        @GET("mobile/patients/{patientId}/questionnaires")
        fun getPatientQuestionnaire(
            @Header("Authorization") token: String,
            @Path("patientId") patientId: Int,
            @Query("per_page") count: Int,
            @Query("page") pageNo: Int
        ): Single<QuestionnaireByPatientResponse>

        @GET("mobile/questionnaire/answer/{questionId}")
        fun getPatientQuestionnaireAnswer(
            @Header("Authorization") token: String,
            @Path("questionId") patientId: Int
        ): Single<QuestionnaireDetailResponse>

        @GET("mobile/patients/documents/{patientId}")
        fun getDocuments(
            @Header("Authorization") token: String,
            @Path("patientId") patientId: Int,
            @Query("per_page") count: Int,
            @Query("page") pageNo: Int
        ): Single<DocumentsListResponse>

        @Multipart
        @POST("mobile/patients/documents/{patientId}")
        fun uploadDocument(
            @Header("Authorization") token: String,
            @Path("patientId") patientId: Int,
            @Part("title") title: RequestBody?,
            @Part profilePic: MultipartBody.Part?
        ): Single<DocumentUploadResponse>

        @Multipart
        @POST("mobile/patients/documents/update/{patientId}/{documentId}")
        fun updateDocument(
            @Header("Authorization") token: String,
            @Path("patientId") patientId: Int,
            @Path("documentId") documentId: Int,
            @Part("title") title: RequestBody?,
            @Part profilePic: MultipartBody.Part?
        ): Single<DocumentUploadResponse>

        //mobile/patients/documents/{{patient_id}}/{{document_id}}
        @DELETE("mobile/patients/documents/{patientId}/{documentId}")
        fun deleteDocument(
            @Header("Authorization") token: String, @Path("patientId") patientId: Int,
            @Path("documentId") documentId: Int
        ): Single<DocumentDeleteResponse>

        @GET("mobile-questionnaires/{serviceId}")
        fun getQuestionnaireForService(
            @Header("Authorization") token: String,
            @Path("serviceId") serviceId: Int
        ): Single<ServiceQuestionnaireResponse>

        @GET("mobile/members")
        fun getTimeSlot(
            @Header("Authorization") token: String,
            @Query("from_date") fromDate: String,
            @Query("to_date") toDate: String,
            @Query("timezone") timezone: String,
            @Query("time_slot") timeSlot: Int = 10,
            @Query("sort_column") sortColumn: String = "member_id",
            @Query("sort_order") sortOrder: String = "D",
            @Query("per_page") perPage: Int = 10,
            @Query("page") page: Int = 1
        ): Single<MemberListResponse>

        @POST("mobile/questionnaire/answers")
        fun submitAnswer(
            @Header("Authorization") token: String,
            @Body answerRequest: AnswerSubmissionRequest
        ): Single<AnswerSubmissionResponse>


        @POST("mobile/appointment-cancel")
        fun cancelAppointment(
            @Header("Authorization") token: String,
            @Body appointmentRequest: CancelAppointmentRequest
        ): Single<CancelAppointmentResponse>

        @POST("mobile/appointment-reschedule")
        fun rescheduleAppointment(
            @Header("Authorization") token: String,
            @Body rescheduleAppointmentRequest: RescheduleAppointmentRequest
        ): Single<RescheduleAppointmentResponse>

        @POST("mobile/encounters")
        fun getEncountersList(
            @Header("Authorization") token: String,
            @Body request: EncountersRequest
        ): Single<EncountersResponse>

        @POST("mobile/prescriptions")
        fun getPrescriptionsList(
            @Header("Authorization") token: String,
            @Body request: PrescriptionsRequest
        ): Single<PrescriptionsResponse>

        @GET("mobile/notifications")
        fun getNotificationList(
            @Header("Authorization") token: String,
            @Query("patient_id") patientId: String,
            @Query("sort_order") sortOrder: String = "desc",
            @Query("per_page") perPage: Int = 10,
            @Query("page") page: Int = 1
        ): Single<NotificationListResponse>

        @GET("mobile/notifications/count")
        fun getBadgeCount(
            @Header("Authorization") token: String,
            @Query("patient_id") patientId: String
        ): Single<BadgeCountResponse>

        @GET("mobile/notifications/count/clear")
        fun clearBadgeCount(
            @Header("Authorization") token: String,
            @Query("patient_id") patientId: String
        ): Single<ClearBadgeCountResponse>

        @GET("getAppointmentMessageChat/{doctorId}")
        fun getChatHistory(
            @Header("Authorization") token: String,
            @Path("doctorId") doctorId: Int,
            @Query("patient_id") patient_id: Int,
            @Query("per_page") per_page: Int,
            @Query("page") pageNo: Int,
            @Query("appointment_id") appointment_id: Int,
            @Query("sort_order") sort_order: String = "desc"
        ): Single<ChatListResponse>

        @POST("initiatePayment")
        fun initPayment(
            @Header("Authorization") token: String,
            @Body paymentRequest: PaymentInitRequest
        ): Single<PaymentInitResponse>

        @DELETE("mobile/notification/destroy/{notificationId}")
        fun deleteNotification(
            @Header("Authorization") token: String,
            @Path("notificationId") notificationId: Int
        ): Single<DeleteNotificationResponse>

        @POST("generateOtp")
        fun sendOtp(
            @Body sendOtpRequest: SendOtpRequest
        ): Single<SendOtpResponse>

        @POST("create-password")
        fun setupPassword(
            @Body setupPasswordRequest: SetupPasswordRequest
        ): Single<SetupPasswordResponse>


        @GET("mobile/labs")
        fun getLabList(
            @Header("Authorization") token: String,
            @Query("sort_column") sortColumn: String = "id",
            @Query("sort_order") sortOrder: String = "asc",
            @Query("per_page") perPage: Int = 10,
            @Query("search_key") searchKey: String,
            @Query("is_search") isSearch: Boolean = false,
            @Query("page") page: Int = 1,
            @Query("client_id") clientId: Int
        ): Single<LabListResponse>

        @POST("mobile/lab/tests")
        fun getLabTestsList(
            @Header("Authorization") token: String,
            @Body labTestRequest: LabTestListRequest
        ): Single<LabTestListResponse>


        @POST("mobile/lab/payment")
        fun saveTestPayment(
            @Header("Authorization") token: String,
            @Body testSavePaymentReq: TestPaymentSaveRequest
        ): Single<TestPaymentSaveResponse>

        @POST("mobile/store/test-info")
        fun storeTestInfo(
            @Header("Authorization") token: String,
            @Body storeTestInfoRequest: StoreTestInfoRequest
        ): Single<StoreTestInfoResponse>

        @GET("mobile/clients")
        fun getClients(
            @Header("Authorization") token: String,
            @Query("sort_column") sortingKey: String = "id",
            @Query("sort_order") sortOrder: String = "A",
            @Query("page") page: Int,
            @Query("per_page") count: Int
        ): Single<ClientResponse>


        @GET("mobile/services/types")
        fun getServiceTypes(
            @Header("Authorization") token: String,
            @Query("sort_column") sortingKey: String = "id",
            @Query("sort_order") sortOrder: String = "A",
            @Query("page") page: Int,
            @Query("per_page") count: Int
        ): Single<ServiceTypeResponse>

        @GET("mobile/lab/orders")
        fun getLabOrdersList(
            @Header("Authorization") token: String,
            @Query("sort_column") sortColumn: String = "id",
            @Query("sort_order") sortOrder: String = "asc",
            @Query("per_page") perPage: Int = 10,
            @Query("page") page: Int = 1,
            @Query("patient_id") patientId: Int
        ): Single<LabOrderListResponse>

        @GET("mobile/lab/order-detail")
        fun getLabOrdersDetail(
            @Header("Authorization") token: String,
            @Query("order_id") orderId: Int
        ): Single<LabOrderDetailResponse>*/


    @POST("auth/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<SignUpResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<LoginResponse>

    @FormUrlEncoded
    @POST("auth/forgot-password")
    fun forgotPassword(@Field("email") email: String): Single<ForgotPasswordResponse>

    @FormUrlEncoded
    @POST("auth/social")
    fun socialLogin(
        @Field("appleId") appleId: String = "",
        @Field("facebookId") facebookId: String = ""
    ): Single<SocialLoginResponse>

    @GET("misc/interests")
    fun getInterestList(@Header("Authorization") token: String = ""): Single<InterestResponse>
}
