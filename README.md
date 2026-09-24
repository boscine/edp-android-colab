LICEO DE CAGAYAN UNIVERSITY
College of Information Technology
Fundamentals of Enterprise Data Management · Event-Driven Programming · S.Y. 2026–2027, 1st Semester
SEMI-FINAL LABORATORY EXAM
LiceoAccount
Login, Create Account and Profile using a real REST API
Instructor	Mr. Ernesto Razo Jr.	Duration	90 minutes
Course	______________________	Points	100  (+10 bonus)
Topic	Lecture 11 · Networking & REST APIs	Git branch	semi-final-exam

Name		Section		Score
GitHub repo		Date		_____ / 100

EXAM GUIDELINES
Read this whole page before you type anything. It takes three minutes and it will save you points.
•Work alone. This is an individual exam. Do not share code, screens or answers with a classmate, in person or by chat.
•Allowed: the Lecture 11 slides, your own lab activities (9, 10 and 11), and the official Android and Kotlin documentation.
•Not allowed: Copying a classmate's repository.
•Use the branch semi-final-exam for all your work. Work pushed to main or to another branch is marked as a wrong branch (see Deductions).
•Commit and push once, at the end (see How to Submit). You may commit more often if you like, but it is not required.
•Screenshots are required. Save them in a folder named screenshots using the exact file names in the Screenshots section.
•Test data only. Use a made-up name, email and password (for example juan.test01@liceo.test / secret123). Never type a real password — see the warning in “Meet the Exam API”.
•Red errors are normal until Part F. Your files use each other, so the project will not build until every part is done. Build and run at the end of Part F.


WHAT YOU WILL BUILD
LiceoAccount is a small Android app with three screens. It talks to a real online API that the whole class shares. When you create an account, it is saved on the internet, and you can log in with it from any phone.
Screen	What the user does	What the app shows
1 · Login	Types an email and a password, then taps Log in.	A spinner while waiting. A red message if the email or password is wrong. The Profile screen if they are right.
2 · Create account	Types full name, email, password and birthdate, then taps Create account.	A red message if something is missing or wrong. A green message “Account created for …” when it is saved.
3 · Profile	Reads their details, then taps Log out.	A green card: “You successfully logged in!”, then the full name, email, birthdate and user ID. Never the password.

HOW THE FILES WORK TOGETHER
This is the same layering you learned in Lecture 11. Each box only talks to the box under it:
Screen  →  AuthViewModel  →  UserRepository  →  Retrofit (UserApiService)  →  MockAPI server
The screen never touches Retrofit. The ViewModel never sees an IOException. The repository turns every problem into a named result.
WHAT YOU WILL BE ABLE TO SHOW
1.APPLY kotlinx.serialization to turn the users JSON into Kotlin data classes (a DTO for reading and one for sending).
2.BUILD a Retrofit interface with a GET that uses @Query and a POST that uses @Body.
3.IMPLEMENT a repository that returns a named result (AppResult) instead of throwing network errors.
4.WRITE a mapper that turns the server's UserDto into the app's own User class — and leaves the password behind.
5.BUILD a ViewModel that checks the user's input and exposes one UI state for the screen to draw.
6.CONSTRUCT three Compose screens that react to Loading, Error, AccountCreated and LoggedIn states.
Suggested time plan
Part	What you do	TODOs	Minutes
A	Get ready: branch and folders	—	5
B	Data classes and the mapper	1 – 3	10
C	Retrofit interface and NetworkModule	4 – 5	10
D	Repository: login and create account	6 – 7	20
E	ViewModel: checking input, choosing a state	8 – 9	10
F	The three screens, then run and test	10 – 12	30
—	Screenshots, commit, push	—	5
G	Bonus (only if everything above works)	13 – 14	extra
MEET THE EXAM API
Your instructor created one shared users resource on MockAPI. You do not need an account or an API key. Every student uses the same address.
Item	Value
Base URL	https://6a9b85920ad174e139e8b0f2.mockapi.io/chat-messaging/api/v1/
Resource	users
Create an account	POST users with the new user as JSON in the body. The server answers 201 Created and sends the saved user back, now with an id.
Find by email	GET users?email=juan.test01@liceo.test — answers with a JSON array of matching users.
Fields	id (text, made by the server) · fullname · email · password · birthdate — spelled exactly like this, all lowercase.

What your app sends when creating an account, and what the server sends back:
Example request and response
// POST users  —  the body your app sends
{
  "fullname": "Juan Dela Cruz",
  "email": "juan.test01@liceo.test",
  "password": "secret123",
  "birthdate": "2004-05-17"
}

// 201 Created  —  the body the server sends back
{
  "fullname": "Juan Dela Cruz",
  "email": "juan.test01@liceo.test",
  "password": "secret123",
  "birthdate": "2004-05-17",
  "id": "12"
}

Try it now: open the base URL followed by users in your browser. You will see the accounts your classmates have already made. Seeing the data before you code is the fastest way to get the data class right.
THREE THINGS THIS API DOES THAT YOU MUST HANDLE
1 · The email search is loose. ?email=juan also finds juan@x.com and ajuan@y.com, because MockAPI searches for text that *contains* your value. So after searching, your code must check for the exact email itself (TODO 6 and 7).
2 · “Nobody found” may come back as 404. MockAPI can answer 404 Not Found instead of an empty list [] when a search matches nobody. The given function findUsers() in Part D turns that 404 into an empty list for you.
3 · The `id` is text, not a number. MockAPI sends "id": "12" with quotes. Use String for it, not Int.

WARNING: THIS IS A TEACHING API, NOT A REAL LOGIN SYSTEM
Passwords are stored as plain text and anyone with the URL can read every account. That is why you must use test data only. A real app never downloads passwords: it sends them over HTTPS to its own server, which stores only a *hash* (a scrambled fingerprint) and answers “yes” or “no”. We check the password in the app today only because MockAPI cannot do it for us.

PART A · GET READY
Your project is already set up — Retrofit, kotlinx.serialization, the INTERNET permission and the ViewModel library are in it. Part A only moves you onto the exam branch and makes the folders.
Step A1 · Open your project and create the branch
Open your project in Android Studio. Open the Terminal tab at the bottom and type these commands one line at a time:
Terminal
# 1. start from your latest work
git checkout main

# 2. create the exam branch and move onto it
git checkout -b semi-final-exam

# 3. check: this must print  * semi-final-exam
git branch

If your main branch has another name (for example master), use that name in the first command.
Step A2 · Make the folders
Right-click your main package → New › Package and create the packages below. Then create each file as you reach it in Parts B to F.
Where every file goes
com.liceo.account
├── MainActivity.kt                 Part F  (GIVEN, replace the old one)
├── core
│   └── AppResult.kt                Part D  (GIVEN)
├── data
│   ├── UserRepository.kt           Part D  (TODO 6, 7)
│   └── network
│       ├── UserApiService.kt       Part C  (TODO 4)
│       ├── NetworkModule.kt        Part C  (TODO 5)
│       └── dto
│           ├── UserDto.kt          Part B  (TODO 1)
│           └── UserMappers.kt      Part B  (TODO 3)
├── domain
│   └── model
│       └── User.kt                 Part B  (TODO 2)
└── ui
    ├── AuthUiState.kt              Part E  (GIVEN)
    ├── AuthViewModel.kt            Part E  (TODO 8, 9)
    ├── LoginScreen.kt              Part F  (TODO 10)
    ├── RegisterScreen.kt           Part F  (TODO 11)
    ├── ProfileScreen.kt            Part F  (TODO 12)
    └── LiceoAccountApp.kt          Part F  (GIVEN)

YOUR PACKAGE NAME
The code in this handout uses the package com.liceo.account. If your project uses another package name, keep yours and change the first part of every package and import com.liceo.account... line to match. Android Studio will underline any line you missed in red.

CHECKPOINT A
git branch shows * semi-final-exam · the packages core, data, domain and ui exist.
PART B · THE DATA CLASSES
You need three classes for one user, because three places need three different shapes: what the server sends (UserDto), what you send to create an account (NewUserDto), and what your screens use (User).
TODO 1   The two DTOs   (5 points)
Create the file data/network/dto/UserDto.kt and type this. Fill in the TODO lines.
data/network/dto/UserDto.kt
package com.liceo.account.data.network.dto

import kotlinx.serialization.Serializable

// What the SERVER sends. We do not trust it, so every field may be missing.
// TODO 1a: put the @Serializable annotation on the line above the class
data class UserDto(
    // TODO 1b: id — a String? with the default value null
    // TODO 1c: fullname, email, password, birthdate — each a String? = null
)

// What WE send when creating an account. We made it, so nothing is missing.
// TODO 1d: put the @Serializable annotation on the line above the class
data class NewUserDto(
    // TODO 1e: fullname, email, password, birthdate — each a plain String
)
HINTS
1a  Write @Serializable on its own line, directly above data class UserDto(. Without it you get “Serializer has not been found”.
1b  val id: String? = null, — the ? means “may be null” and = null means “may be missing”. MockAPI ids are text, so not Int.
1c  Same pattern four times: val fullname: String? = null, and so on. The names must match the JSON keys exactly — fullname, not fullName.
1d  Same as 1a, above data class NewUserDto(.
1e  val fullname: String, then email, password, birthdate. No ? and no default, and no id — the server makes the id.
TODO 2   The app's own User class   (3 points)
Create the file domain/model/User.kt:
domain/model/User.kt
package com.liceo.account.domain.model

// What the SCREENS use. No @Serializable, no ?, and NO password.
data class User(
    // TODO 2a: id as a String
    // TODO 2b: fullName, email, birthdate — each a String
)
HINTS
2a  val id: String,
2b  val fullName: String, val email: String, val birthdate: String — here you may use the Kotlin style fullName, because this class is yours, not the server's.
Why  There is no password field on purpose. If the class has no password, no screen can ever show it by mistake.
TODO 3   The mapper: UserDto → User   (4 points)
Create the file data/network/dto/UserMappers.kt:
data/network/dto/UserMappers.kt
package com.liceo.account.data.network.dto

import com.liceo.account.domain.model.User

// Turns the server's messy shape into the app's clean shape.
fun UserDto.toDomain(): User = User(
    // TODO 3a: id — use id, or "" when it is null
    // TODO 3b: fullName — fullname with spaces trimmed, or "(no name)" when null
    // TODO 3c: email — email with spaces trimmed, or "" when null
    // TODO 3d: birthdate — birthdate, or "(not set)" when null
    // Notice: the password is NOT copied. The screen never needs it.
)
HINTS
3a  id = id ?: "", — the ?: (Elvis operator) means “if the left side is null, use the right side”.
3b  fullName = fullname?.trim() ?: "(no name)", — ?.trim() only runs when the value is not null.
3c  email = email?.trim() ?: "",
3d  birthdate = birthdate ?: "(not set)" — the last argument needs no comma.
PART C · RETROFIT
TODO 4   Describe the API as an interface   (6 points)
Create the file data/network/UserApiService.kt. There is no networking code here — only a description. Retrofit writes the rest.
data/network/UserApiService.kt
package com.liceo.account.data.network

import com.liceo.account.data.network.dto.NewUserDto
import com.liceo.account.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    // TODO 4a: GET "users" with a @Query("email") parameter named email.
    //          Call the function findByEmail. It returns List<UserDto>.

    // TODO 4b: POST "users" with a @Body parameter named user of type NewUserDto.
    //          Call the function createUser. It returns UserDto.
}
HINTS
4a  Two lines: @GET("users") then suspend fun findByEmail(@Query("email") email: String): List<UserDto>. It is a List because the server answers with an array [ ].
4b  @POST("users") then suspend fun createUser(@Body user: NewUserDto): UserDto. The answer is one object { } — the saved user with its new id.
Watch  No slash at the front: "users", not "/users". A leading slash throws away part of the base URL and you get a 404.
TODO 5   Build Retrofit once for the whole app   (6 points)
Create the file data/network/NetworkModule.kt. It is an object, so there is only ever one.
data/network/NetworkModule.kt
package com.liceo.account.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // TODO 5a: put the base URL from "Meet the Exam API" between the quotes
    private const val BASE_URL = ""

    private val json = Json {
        // TODO 5b: set ignoreUnknownKeys and coerceInputValues to true
    }

    // GIVEN (read it, do not change it): prints every request in Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val userApi: UserApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        // TODO 5c: finish the chain with .build() and .create(...)
}
HINTS
5a  Copy the whole base URL, including https:// and the slash at the end. Missing slash = crash at startup (“baseUrl must end in /”).
5b  Two lines inside the braces: ignoreUnknownKeys = true and coerceInputValues = true. The first stops a crash when the server adds a field such as createdAt.
5c  Add .build() on one line and .create(UserApiService::class.java) on the next. create is where Retrofit writes the class for your interface.
Note  Level.BODY prints the password into Logcat. That is fine for the exam, but a published app must turn it off.
PART D · THE REPOSITORY
The repository is the canteen counter from Lecture 11: the ViewModel asks for a user and does not care how the counter gets it. It returns a named result instead of throwing an exception.
GIVEN · AppResult
Create the file core/AppResult.kt and copy it exactly:
core/AppResult.kt
package com.liceo.account.core

// GIVEN (read it, do not change it)
sealed interface AppResult<out T> {

    data class Success<T>(val data: T) : AppResult<T>

    sealed interface Failure : AppResult<Nothing> {
        data object NoInternet : Failure   // no signal, or the host was not found
        data object Timeout : Failure      // the server was too slow
        data object WrongLogin : Failure   // the email or the password is wrong
        data object EmailTaken : Failure   // an account already uses this email
        data class Unknown(val msg: String?) : Failure
    }
}
The repository · first half is GIVEN
Create the file data/UserRepository.kt and copy this first half exactly. Read the comments — you will use findUsers() and safeCall { } in TODO 6 and 7.
data/UserRepository.kt  (part 1 of 2)
package com.liceo.account.data

import com.liceo.account.core.AppResult
import com.liceo.account.data.network.NetworkModule
import com.liceo.account.data.network.UserApiService
import com.liceo.account.data.network.dto.NewUserDto
import com.liceo.account.data.network.dto.UserDto
import com.liceo.account.data.network.dto.toDomain
import com.liceo.account.domain.model.User
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(
    private val api: UserApiService = NetworkModule.userApi
) {

    // GIVEN (read it, do not change it)
    // MockAPI may answer 404 instead of [] when a search finds nobody.
    // This turns that 404 into an empty list, so "nobody found" is not an error.
    private suspend fun findUsers(email: String): List<UserDto> =
        try {
            api.findByEmail(email)
        } catch (e: HttpException) {
            if (e.code() == 404) emptyList() else throw e
        }

    // GIVEN (read it, do not change it)
    // Runs your block and turns every network exception into a named Failure.
    // The LAST line of your block is the result that comes back.
    private inline fun <T> safeCall(block: () -> AppResult<T>): AppResult<T> =
        try {
            block()
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: HttpException) {
            AppResult.Failure.Unknown("Server error ${e.code()}")
        } catch (e: SerializationException) {
            AppResult.Failure.Unknown("The server sent data we could not read.")
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        }

    // ...continue with TODO 6 and TODO 7 on the next pages...


TODO 6   Log in   (9 points)
Keep typing inside the same class, under the given code:
data/UserRepository.kt  (part 2 — TODO 6)
    // Finds the user with this email, then checks the password.
    suspend fun login(email: String, password: String): AppResult<User> = safeCall {
        // TODO 6a: search the server: val matches = findUsers(...the trimmed email...)
        // TODO 6b: val found = the FIRST user in matches whose email equals the typed
        //          email (ignore upper/lower case) AND whose password equals password
        // TODO 6c: LAST line: if found is null → AppResult.Failure.WrongLogin
        //                     otherwise       → AppResult.Success(found.toDomain())
    }
HINTS
6a  val matches = findUsers(email.trim()) — trim() removes spaces the user typed by accident.
6b  val found = matches.firstOrNull { it.email.equals(email.trim(), ignoreCase = true) && it.password == password } — firstOrNull gives the first match, or null when there is none.
6c  if (found == null) AppResult.Failure.WrongLogin else AppResult.Success(found.toDomain()) — do not write return; the last line of the block is the answer.
Why  We say “Wrong email or password” for both mistakes on purpose. Saying “this email does not exist” would tell a stranger which emails have accounts.
TODO 7   Create an account   (9 points)
Still inside the same class, under login. The last } closes the class.
data/UserRepository.kt  (part 3 — TODO 7)
    // Creates the account — but only if nobody already uses this email.
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        birthdate: String
    ): AppResult<User> = safeCall {
        // TODO 7a: val taken = true when ANY user from findUsers(...) has exactly
        //          this email (ignore upper/lower case)
        // TODO 7b: LAST expression starts: if (taken) → AppResult.Failure.EmailTaken
        // TODO 7c: else → build a NewUserDto, send it with api.createUser(...)
        //          and return AppResult.Success(saved.toDomain())
    }
}
HINTS
7a  val taken = findUsers(email.trim()).any { it.email.equals(email.trim(), ignoreCase = true) } — any { } is true if at least one item matches.
7b  Start the last expression with if (taken) { AppResult.Failure.EmailTaken } else { ... }.
7c  Inside the else braces: val saved = api.createUser(NewUserDto(fullname = fullName.trim(), email = email.trim(), password = password, birthdate = birthdate.trim())) and then, as the last line, AppResult.Success(saved.toDomain()).
Check  Named arguments (fullname = ...) stop you from mixing up four Strings. The left name is the DTO field (lowercase fullname); the right is the function parameter (fullName).
PART E · THE VIEWMODEL
GIVEN · AuthUiState
One sealed interface lists every state a screen can be in. Create ui/AuthUiState.kt:
ui/AuthUiState.kt
package com.liceo.account.ui

import com.liceo.account.domain.model.User

// GIVEN (read it, do not change it)
sealed interface AuthUiState {
    data object Idle : AuthUiState                          // nothing is happening yet
    data object Loading : AuthUiState                       // waiting for the server
    data class Error(val message: String) : AuthUiState     // show this in red
    data class AccountCreated(val name: String) : AuthUiState // show this in green
    data class LoggedIn(val user: User) : AuthUiState       // show the Profile screen
}

The ViewModel · first half is GIVEN
Create ui/AuthViewModel.kt and copy this first half exactly:
ui/AuthViewModel.kt  (part 1 of 2)
package com.liceo.account.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceo.account.core.AppResult
import com.liceo.account.data.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    // GIVEN (read it, do not change it)
    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    private val datePattern = Regex("""\d{4}-\d{2}-\d{2}""")   // like 2004-05-17

    fun clearMessage() { uiState = AuthUiState.Idle }

    fun logout() { uiState = AuthUiState.Idle }

    private fun messageFor(failure: AppResult.Failure): String = when (failure) {
        AppResult.Failure.NoInternet -> "No internet connection. Please try again."
        AppResult.Failure.Timeout -> "The server was too slow. Please try again."
        AppResult.Failure.WrongLogin -> "Wrong email or password."
        AppResult.Failure.EmailTaken -> "An account with this email already exists."
        is AppResult.Failure.Unknown -> "Something went wrong: ${failure.msg}"
    }

    // ...continue with TODO 8 and TODO 9 on the next pages...

Your ViewModel must use these exact messages. The checklist and the screenshots look for them.
When	Message (type it exactly)
Login: email or password is blank	Please enter your email and password.
Create account: any of the four fields is blank	Please fill in all four fields.
Create account: the email has no @	Please enter a valid email.
Create account: password shorter than 6 characters	Password must be at least 6 characters.
Create account: birthdate is not YYYY-MM-DD	Birthdate must look like 2004-05-17.


TODO 8   login() — check the input, then ask the repository   (7 points)
Keep typing inside the same class:
ui/AuthViewModel.kt  (part 2 — TODO 8)
    fun login(email: String, password: String) {
        // TODO 8a: if email or password is blank → set uiState to an Error
        //          with the login message from the table, then return
        // TODO 8b: set uiState to Loading
        // TODO 8c: launch a coroutine, call repository.login(email, password)
        //          and turn the result into a state:
        //          Success → AuthUiState.LoggedIn(result.data)
        //          Failure → AuthUiState.Error(messageFor(result))
    }
HINTS
8a  if (email.isBlank() || password.isBlank()) { uiState = AuthUiState.Error("Please enter your email and password."); return }
8b  uiState = AuthUiState.Loading — the screen shows the spinner as soon as this line runs.
8c  viewModelScope.launch { uiState = when (val result = repository.login(email, password)) { is AppResult.Success -> AuthUiState.LoggedIn(result.data); is AppResult.Failure -> AuthUiState.Error(messageFor(result)) } } — write each -> branch on its own line; the semicolon here only saves space.
Why  viewModelScope cancels the call if the user leaves, and the suspend call runs off the main thread, so the app never freezes.
TODO 9   register() — check four fields, then ask the repository   (9 points)
Under login. The last } closes the class.
ui/AuthViewModel.kt  (part 3 — TODO 9)
    fun register(fullName: String, email: String, password: String, birthdate: String) {
        // TODO 9a: any of the four is blank          → Error, then return
        // TODO 9b: email does not contain "@"        → Error, then return
        // TODO 9c: password is shorter than 6        → Error, then return
        // TODO 9d: birthdate does not match datePattern → Error, then return
        // TODO 9e: Loading, then launch and call repository.register(...)
        //          Success → AuthUiState.AccountCreated(result.data.fullName)
        //          Failure → AuthUiState.Error(messageFor(result))
    }
}
HINTS
9a  if (fullName.isBlank() || email.isBlank() || password.isBlank() || birthdate.isBlank()) { uiState = AuthUiState.Error("Please fill in all four fields."); return }
9b  if (!email.contains("@")) { ... "Please enter a valid email." ...; return }
9c  if (password.length < 6) { ... "Password must be at least 6 characters." ...; return }
9d  if (!datePattern.matches(birthdate.trim())) { ... "Birthdate must look like 2004-05-17." ...; return }
9e  Copy the shape of 8b and 8c. Call repository.register(fullName, email, password, birthdate). On Success use AuthUiState.AccountCreated(result.data.fullName).
Order  Keep the checks in this order. Each one ends with return, so only the first problem is shown.
PART F · THE THREE SCREENS
GIVEN · the app shell and MainActivity
Create ui/LiceoAccountApp.kt. It decides which screen to show. The Profile screen appears by itself when the state becomes LoggedIn.
ui/LiceoAccountApp.kt
package com.liceo.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

// GIVEN (read it, do not change it)
@Composable
fun LiceoAccountApp(vm: AuthViewModel = viewModel()) {
    var screen by rememberSaveable { mutableStateOf("login") }
    val state = vm.uiState

    if (state is AuthUiState.LoggedIn) {
        ProfileScreen(
            user = state.user,
            onLogout = { vm.logout(); screen = "login" }
        )
    } else if (screen == "register") {
        RegisterScreen(
            state = state,
            onCreate = vm::register,
            onGoToLogin = { vm.clearMessage(); screen = "login" }
        )
    } else {
        LoginScreen(
            state = state,
            onLogin = vm::login,
            onGoToRegister = { vm.clearMessage(); screen = "register" }
        )
    }
}

Replace everything in MainActivity.kt with this. If your theme has another name, use the one in ui/theme/Theme.kt.
MainActivity.kt
package com.liceo.account

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.liceo.account.ui.LiceoAccountApp
import com.liceo.account.ui.theme.LiceoAccountTheme

// GIVEN (read it, do not change it)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiceoAccountTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LiceoAccountApp()
                }
            }
        }
    }
}


TODO 10   The Login screen   (7 points)
Create ui/LoginScreen.kt. The layout is given; you add the parts inside the Column.
ui/LoginScreen.kt
package com.liceo.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: AuthUiState,
    onLogin: (String, String) -> Unit,
    onGoToRegister: () -> Unit
) {
    // GIVEN: what the user types lives here
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isLoading = state is AuthUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("LiceoAccount", style = MaterialTheme.typography.headlineMedium)
        Text("Log in to your account")

        // TODO 10a: OutlinedTextField for the email (label "Email")
        // TODO 10b: OutlinedTextField for the password (label "Password"), hidden
        // TODO 10c: if state is AuthUiState.Error → show state.message in red
        // TODO 10d: Button "Log in" → onLogin(email, password), disabled while loading;
        //           while loading show a small CircularProgressIndicator instead of text
        // TODO 10e: TextButton "No account yet? Create one" → onGoToRegister()
    }
}
HINTS
10a  OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
10b  Same as 10a with password, label "Password", KeyboardType.Password, and add visualTransformation = PasswordVisualTransformation() so the text shows as dots.
10c  if (state is AuthUiState.Error) { Text(state.message, color = MaterialTheme.colorScheme.error) }
10d  Button(onClick = { onLogin(email, password) }, enabled = !isLoading, modifier = Modifier.fillMaxWidth()) { if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) else Text("Log in") }
10e  TextButton(onClick = onGoToRegister) { Text("No account yet? Create one") }


TODO 11   The Create Account screen   (7 points)
Create ui/RegisterScreen.kt. It uses the same pieces as the Login screen, four times.
ui/RegisterScreen.kt
package com.liceo.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    state: AuthUiState,
    onCreate: (String, String, String, String) -> Unit,
    onGoToLogin: () -> Unit
) {
    // TODO 11a: four rememberSaveable text states: fullName, email, password, birthdate
    val isLoading = state is AuthUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create account", style = MaterialTheme.typography.headlineMedium)

        // TODO 11b: OutlinedTextField "Full name"
        // TODO 11c: OutlinedTextField "Email" (email keyboard)
        // TODO 11d: OutlinedTextField "Password" (hidden)
        // TODO 11e: OutlinedTextField "Birthdate" with placeholder "YYYY-MM-DD"
        // TODO 11f: Error → red message. AccountCreated → green message:
        //           "Account created for <name>. You can now log in."
        // TODO 11g: Button "Create account" → onCreate(fullName, email, password,
        //           birthdate), disabled while loading (spinner like TODO 10d)
        // TODO 11h: TextButton "Already have an account? Log in" → onGoToLogin()
    }
}
HINTS
11a  Copy the two lines from the Login screen and make four: var fullName by rememberSaveable { mutableStateOf("") }, then email, password, birthdate.
11b  Like 10a, with fullName and label "Full name". No keyboardOptions needed.
11c  Exactly like 10a.
11d  Exactly like 10b.
11e  Like 11b with birthdate, label "Birthdate", and placeholder = { Text("YYYY-MM-DD") }. A date picker is not required.
11f  Two ifs: if (state is AuthUiState.Error) Text(state.message, color = MaterialTheme.colorScheme.error) and if (state is AuthUiState.AccountCreated) Text("Account created for ${state.name}. You can now log in.", color = Color(0xFF2E7D32)).
11g  Copy 10d. Change the onClick to onCreate(fullName, email, password, birthdate) and the text to "Create account".
11h  TextButton(onClick = onGoToLogin) { Text("Already have an account? Log in") }


TODO 12   The Profile screen   (7 points)
Create ui/ProfileScreen.kt. This is the screen the user sees after a successful login.
ui/ProfileScreen.kt
package com.liceo.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.liceo.account.domain.model.User

@Composable
fun ProfileScreen(user: User, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO 12a: a green Card that says "You successfully logged in!"
        //           and under it "Welcome back, <fullName>."
        // TODO 12b: Text "My Profile" as a heading
        // TODO 12c: four ProfileRow(...) calls: Full name, Email, Birthdate, User ID
        // BONUS TODO 14d (Part G only): an "Age" row goes here
        // TODO 12d: Button "Log out" → onLogout()
    }
}

// GIVEN (read it, do not change it): one label with its value under it
@Composable
fun ProfileRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
HINTS
12a  Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text("You successfully logged in!", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20)); Text("Welcome back, ${user.fullName}.") } }
12b  Text("My Profile", style = MaterialTheme.typography.headlineSmall)
12c  ProfileRow("Full name", user.fullName), then "Email" with user.email, "Birthdate" with user.birthdate, "User ID" with user.id. There is no password to show — and that is correct.
12d  Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) { Text("Log out") }

CHECKPOINT F — NOW RUN IT
Click Run ▶. Go through the whole Test Checklist below and take the screenshots.
Open the Logcat tab and type okhttp in its search box. You will see every request and answer — the fastest way to find a wrong URL or a wrong field name.
PART G · BONUS (+10)
Do this only if Parts A to F work and your screenshots are taken. Bonus points count only when every core part runs.
TODO 13   Show / hide the password on the Login screen   (+4 points)
In LoginScreen.kt, add one state line under the other two, and change the password field from TODO 10b:
ui/LoginScreen.kt  (bonus change)
    // add under: var password by rememberSaveable { ... }
    // TODO 13a: a Boolean state showPassword, starting at false

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        singleLine = true,
        // TODO 13b: visualTransformation — no dots when showPassword is true
        // TODO 13c: trailingIcon — a TextButton reading "Show" or "Hide"
        modifier = Modifier.fillMaxWidth()
    )
HINTS
13a  var showPassword by remember { mutableStateOf(false) }
13b  visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), — add import androidx.compose.ui.text.input.VisualTransformation at the top.
13c  trailingIcon = { TextButton(onClick = { showPassword = !showPassword }) { Text(if (showPassword) "Hide" else "Show") } },
TODO 14   Show the user's age on the Profile screen   (+6 points)
In ProfileScreen.kt, add this function under ProfileRow, then use it at the BONUS line in TODO 12.
ui/ProfileScreen.kt  (bonus function)
// Returns the age in years, or null if the birthdate cannot be read.
fun ageFrom(birthdate: String): Int? {
    // TODO 14a: split "2004-05-17" at "-" into year, month, day as Int?
    //           (use toIntOrNull); if the split does not give 3 parts, return null
    // TODO 14b: read today's year, month and day from Calendar.getInstance()
    // TODO 14c: age = this year - birth year, minus 1 if the birthday
    //           has not happened yet this year; return age
}
HINTS
14a  val parts = birthdate.split("-"), if (parts.size != 3) return null, then val y = parts[0].toIntOrNull() ?: return null and the same for m and d.
14b  val now = java.util.Calendar.getInstance(), val ty = now.get(java.util.Calendar.YEAR), val tm = now.get(java.util.Calendar.MONTH) + 1 (months start at 0), val td = now.get(java.util.Calendar.DAY_OF_MONTH).
14c  var age = ty - y, then if (tm < m || (tm == m && td < d)) age -= 1, then return age.
14d  At the BONUS line in ProfileScreen: ageFrom(user.birthdate)?.let { ProfileRow("Age", "$it years old") } — nothing shows if the birthdate cannot be read.
TEST CHECKLIST
Do every row on the emulator or your phone. Tick it only when you see exactly what the right-hand column says.
#	Do this	You must see	✓
1	Open the app.	The Login screen. No crash.	
2	Tap Log in with both boxes empty.	Red: “Please enter your email and password.”	
3	Tap Create one, then Create account with the Full name empty.	Red: “Please fill in all four fields.”	
4	Type juan as the email (fill the rest).	Red: “Please enter a valid email.”	
5	Type 123 as the password.	Red: “Password must be at least 6 characters.”	
6	Type 05/17/2004 as the birthdate.	Red: “Birthdate must look like 2004-05-17.”	
7	Fill all four correctly and tap Create account.	A spinner, then green: “Account created for …”	
8	Tap Create account again with the same email.	Red: “An account with this email already exists.”	
9	In a browser, open the base URL + users?email= + your email.	Your account, with the right birthdate and an id.	
10	Go to Login. Use your email with a wrong password.	Red: “Wrong email or password.”	
11	Log in with the correct email and password.	Profile: the green “You successfully logged in!” card and four rows. No password anywhere.	
12	Tap Log out.	Back on the Login screen.	
13	Turn on airplane mode, then try to log in.	Red: “No internet connection. Please try again.” The app does not crash.	
SCREENSHOTS
Make a folder named screenshots in the top folder of your project (next to the app folder). Save each picture there as a .png with the exact name below. On the emulator, click the camera icon on the side toolbar; the picture goes to your Desktop — then move it into the folder and rename it.
File name	What the picture must show	Checklist row
01-login-screen.png	The empty Login screen.	1
02-validation-error.png	The Create Account screen with one red validation message (any of rows 3–6).	3 – 6
03-account-created.png	The green “Account created for …” message with your filled form.	7
04-api-record.png	The browser showing your account on the API (the address bar must be visible).	9
05-wrong-password.png	The Login screen with “Wrong email or password.”	10
06-profile.png	The Profile screen: the green success card and all four rows.	11
07-no-internet.png	The no-internet message with the airplane icon visible in the status bar.	13
08-branch.png	Your GitHub page with the branch menu showing semi-final-exam and the screenshots folder.	—
09-bonus-password.png	Bonus only: the Login screen with the password visible and the “Hide” button.	TODO 13
10-bonus-age.png	Bonus only: the Profile screen with the Age row.	TODO 14
HOW TO SUBMIT
When the checklist is done and all screenshots are in the folder, run these in the Terminal:
Terminal
# make sure you are on the exam branch — it must print  * semi-final-exam
git branch

# save everything, including the screenshots folder
git add .
git commit -m "Semi-final exam: login, create account, profile, screenshots"

# upload the branch to GitHub
git push origin semi-final-exam

1.Open your repository on GitHub, switch the branch menu to semi-final-exam, and check that your code and the screenshots folder are there. Take 08-branch.png now, then commit and push once more.
2.Copy the link of that page. It looks like https://github.com/YOUR-USERNAME/YOUR-REPO/tree/semi-final-exam.
3.Submit the link where your instructor tells you, before the time ends: ______________________________________

BEFORE YOU LEAVE THE ROOM
Commits pushed after the exam time ends are not checked. Leave yourself five minutes to push — and if you fix something later in the period, you can simply push again.

RUBRIC
Each row is marked on the four-level scale below the table. The rows add up to 100.
Part	What earns full points	Points
A · Branch and submission	Project builds; work and the screenshots folder are pushed on semi-final-exam; the link is submitted on time.	5
B · TODO 1 · DTOs	UserDto has all five fields nullable with defaults; NewUserDto has four non-null fields; both @Serializable; key names match the JSON.	5
B · TODO 2 · User	Four non-null fields and no password field.	3
B · TODO 3 · Mapper	Every null handled with ?:; name and email trimmed; the password is not copied.	4
C · TODO 4 · Interface	GET users with @Query("email") returning List<UserDto>; POST users with @Body NewUserDto; both suspend; no leading slash.	6
C · TODO 5 · NetworkModule	Correct base URL ending in /; ignoreUnknownKeys and coerceInputValues on; Retrofit built once and create() called.	6
D · TODO 6 · login()	Uses findUsers(); checks the exact email (ignoring case) and the password; returns WrongLogin or Success(User).	9
D · TODO 7 · register()	Refuses a taken email with EmailTaken; sends a trimmed NewUserDto with createUser; returns the mapped User.	9
E · TODO 8 · VM login()	Blank check with the exact message; Loading before the call; viewModelScope.launch; when turns the result into LoggedIn or Error.	7
E · TODO 9 · VM register()	All four checks in order with the exact messages and return; AccountCreated on success; Error from messageFor().	9
F · TODO 10 · Login screen	Two fields (password hidden); red error; button disabled with a spinner while loading; link to Create Account.	7
F · TODO 11 · Create Account screen	Four fields with rememberSaveable; red error and green success message; button with loading state; link back to Login.	7
F · TODO 12 · Profile screen	Green “You successfully logged in!” card with the user's name; four rows; Log out works; the password is never shown.	7
Screenshots 01 – 08	Two points each: correct file name, in screenshots/, and shows exactly what the table asks for.	16
	Total	100

Bonus (Part G)	What earns the points	Points
TODO 13 · Show / hide password	The toggle works and the button text changes; screenshot 09 is present.	+4
TODO 14 · Age	Correct age, including a birthday later this year; a bad date shows no row instead of crashing; screenshot 10 is present.	+6
Bonus points count only when Parts B to F all run. They can make up for points lost elsewhere, but the final score cannot go above 100.
Quality scale — how each row is marked
Level	Share of the row	What it looks like
Excellent	100%	Works exactly as described, and the code follows the given structure.
Good	75%	Works, with one small problem (a wrong message text, a missing trim, a spinner that never shows).
Fair	50%	Written and compiles, but does not work correctly in at least one case from the checklist.
Needs work	0 – 25%	Missing, does not compile, or copied without understanding (you cannot explain it when asked).
Deductions
Problem	Deduction
Work is not on the branch semi-final-exam (pushed to main, or a misspelled branch name).	The 5 Part A points are forfeited and a further −5 is applied (−10 in all).
The app crashes when it opens, when logging in, or when creating an account.	−15
A screen or the ViewModel calls NetworkModule or the API directly, skipping the repository.	−10
Each fault is scored once. Anything a rubric row already describes — a wrong message, a missing check, the password shown on screen, a missing screenshot — is marked by lowering that row's level, and is never deducted again here. The deductions above are only for faults that break the app as a whole, and they do add up.
TROUBLESHOOTING
Most problems in this exam are one-line setup mistakes. Find your message on the left.
The message	What it really means	The fix
Plugin with id org.jetbrains.kotlin.plugin.serialization not found	The serialization plugin is missing from your setup.	Ask your instructor to check your Gradle files before you continue.
Serializer has not been found for type UserDto	No @Serializable, or the plugin is not applied in the app module.	Add the annotation (TODO 1a, 1d); check the plugin line in app/build.gradle.kts.
baseUrl must end in /	BASE_URL is empty or missing its last slash.	Copy the full URL again (TODO 5a).
SecurityException: Permission denied (missing INTERNET permission?)	The manifest line is missing or inside <application>.	Add <uses-permission android:name="android.permission.INTERNET" /> above <application.
Server error 404 when creating an account	Wrong path, or a slash at the front of "/users".	Use @POST("users") with no leading slash.
Expected start of the array	findByEmail returns one UserDto, but the server sends a list.	Return List<UserDto> (TODO 4a).
Always “Wrong email or password.” even when it is right	The compare is too strict, or the account was saved with spaces.	Use trim() and ignoreCase = true; check the record in the browser.
Unresolved reference: viewModel	The lifecycle-viewmodel-compose library is missing.	Add lifecycle-viewmodel-compose to app/build.gradle.kts and Sync.
Unresolved reference: LiceoAccountTheme	Your theme has another name.	Use the name in ui/theme/Theme.kt.
“No internet connection” while Wi-Fi is on	The emulator lost its network, or a Wi-Fi login page is in the way.	Open the API in the emulator's Chrome; cold-boot the emulator if it fails.
