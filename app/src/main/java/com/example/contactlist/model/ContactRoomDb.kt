package

@Database(entities = [Contact::class], version = 1)
abstract class ContactRoomDb: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}