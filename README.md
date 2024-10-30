
# Currency Converter

This currency converter application is built using Kotlin as the primary language and follows a clean architecture approach with MVVM (Model-View-ViewModel). It leverages Retrofit for making network requests to fetch currency conversion data from an open API. User preferences, like the last currency conversion, are stored in SharedPreferences for quick retrieval. Room database is employed to store data from the API locally, allowing offline conversion capability (requires initial network access for data retrieval). Hilt-Dagger is implemented for dependency injection, ensuring a clean and maintainable codebase.


## Installation & Build
    1. Ensure you have the Android Studio IDE installed and configured.

    2. Clone the repository to your local machine.

    3. Open the project in Android Studio.

    4. Connect a compatible Android device or emulator.

    5. Run the application.
    
## Challenging & Limit

- Ensure your Android device or emulator has network connectivity for initial API data retrieval.
- Adjust API keys and endpoints based on your chosen currency conversion API (Free version will limit the number of requests and permissions to use API).
- Consider implementing data caching and refresh mechanisms to improve user experience.
## Demo
![currency_converter_demo](https://github.com/user-attachments/assets/8c0b0dbd-ce59-432f-bf24-46ce293f4425)

