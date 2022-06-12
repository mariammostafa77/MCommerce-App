package com.example.mcommerce.orders.model

data class Customer(
    val accepts_marketing: Boolean,
    val accepts_marketing_updated_at: String,
    val admin_graphql_api_id: String,
    val created_at: String,
    val currency: String,
    val default_address: DefaultAddress,
    val email: String,
    val first_name: String,
    val id: Long,
    val last_name: String,
    val last_order_id: Long,
    val last_order_name: String,
    val marketing_opt_in_level: Any,
    val multipass_identifier: Any,
    val note: Any,
    val orders_count: Int,
    val phone: String,
    val sms_marketing_consent: SmsMarketingConsent,
    val state: String,
    val tags: String,
    val tax_exempt: Boolean,
    val tax_exemptions: List<Any>,
    val total_spent: String,
    val updated_at: String,
    val verified_email: Boolean
)