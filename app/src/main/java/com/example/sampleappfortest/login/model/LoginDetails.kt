package  com.example.sampleappfortest.login.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "LoginDetails")
data class LoginDetails (
    @ColumnInfo(name = "Username")
    @PrimaryKey
    var username: String = "",

    @ColumnInfo(name = "PhoneNo")
var phoneNo: String? = null
        )






