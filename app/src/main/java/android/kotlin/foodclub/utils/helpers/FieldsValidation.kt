package android.kotlin.foodclub.utils.helpers

class FieldsValidation {
    companion object {
        fun checkPassword(content: String): String? {
            if(content.length < 8 || Regex("\\s+").containsMatchIn(content))
                return "At least 8 characters with no space"
            if(!Regex("[A-Z]+").containsMatchIn(content))
                return "At least 1 upper case letter"
            if(!Regex("^(?=.*[!@#\$%^&*])").containsMatchIn(content))
                return "At least 1 special character"
            return null
        }

        fun checkEmail(content: String): String? {
            if(!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(content))
                return "Invalid email address"
            return null
        }

        fun checkUsername(content: String): String? {
            if(!Regex("^[a-zA-Z0-9]+\$").containsMatchIn(content))
                return "Username can only contain letters and numbers"
            return null
        }
    }
}