package com.example.youthspace.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClientProvider {
    val client = createSupabaseClient(
        supabaseUrl = "https://xyrlikqabwldyjlgygmz.supabase.co",
        supabaseKey = "sb_publishable_kAduvfF1-7rVYO9LB2IZUQ_BLnORfb6"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}
