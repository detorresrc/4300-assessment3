package com.detorresrc.tech4300.assessment3_employeemanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

// This is the main database description. It holds the database schema.
@Database(
    entities = [Employee::class], // This is where we list all the entities or tables for the database.
    version = 1, // This is the version of the database. It's used to handle schema migrations.
    exportSchema = false // This means we are not exporting the schema into a separate file.
)
abstract class EmployeeDatabase: RoomDatabase() { // This is the main class that the system uses to create and manage the database.
    abstract fun employeeDao(): EmployeeDao // This is a function that allows us to access the DAO (Data Access Object) for the Employee table.

    companion object { // This is a singleton to prevent having multiple instances of the database opened at the same time.
        @Volatile // This means the value of INSTANCE is always up-to-date and the same to all execution threads.
        private var INSTANCE: EmployeeDatabase? = null // This holds a reference to the database, when one has been created.

        // This function returns the singleton. It'll create a new database the first time it's accessed.
        fun getDatabase(context: Context) : EmployeeDatabase {
            return INSTANCE ?: synchronized(this) { // If INSTANCE is not null, then return it, if it is, then create a new database.
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext, // The context of the application.
                    EmployeeDatabase::class.java, // This is the class that's annotated with @Database.
                    "employee_database" // The name of the database.
                ).build() // Create the database instance.
                INSTANCE = instance // Assign INSTANCE to the newly created database.
                instance // Return instance of the database.
            }
        }
    }
}