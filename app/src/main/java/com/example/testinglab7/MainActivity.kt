
package com.example.testinglab7

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.testinglab7.ui.theme.TestingLab7Theme
import java.net.URL
import java.net.HttpURLConnection

private const val TAG = "TestingLab7"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isDebug = applicationInfo.flags and
                ApplicationInfo.FLAG_DEBUGGABLE != 0

        // Задание 13: StrictMode
        if (isDebug) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            Log.i(TAG, "StrictMode включён")
        }

        if (isDebug) {
            Log.d(TAG, "Debug: приложение запущено")
        }

        Log.i(TAG, "Info: MainActivity создана")
        Log.w(TAG, "Warning: проверка предупреждений")
        Log.e(TAG, "Error: тестовое сообщение об ошибке")

        setContent {
            TestingLab7Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LoggingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoggingScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val isDebug = context.applicationInfo.flags and
            ApplicationInfo.FLAG_DEBUGGABLE != 0

    val dbHelper = remember {
        UserDbHelper(context.applicationContext)
    }

    var usersText by remember {
        mutableStateOf("Нажмите «Показать пользователей»")
    }

    var networkText by remember {
        mutableStateOf("Сетевой запрос ещё не выполнялся")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Лабораторная работа №7",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Тестирование и отладка Android-приложений")
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (isDebug) Log.d(TAG, "Debug: нажата кнопка DEBUG")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log.d() — Debug")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                Log.i(TAG, "Info: нажата кнопка INFO")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log.i() — Info")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                Log.w(TAG, "Warning: нажата кнопка WARNING")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log.w() — Warning")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                Log.e(TAG, "Error: нажата кнопка ERROR")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log.e() — Error")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (isDebug) Log.d(TAG, "Debug: комплексная проверка")
                Log.i(TAG, "Info: комплексная проверка")
                Log.w(TAG, "Warning: комплексная проверка")
                Log.e(TAG, "Error: комплексная проверка")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить все уровни")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val price = 500
                val quantity = 3
                val result = calculateTotal(price, quantity)

                Log.i(TAG, "Цена товара: $price")
                Log.i(TAG, "Количество: $quantity")
                Log.i(TAG, "Результат расчёта: $result")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить расчёт")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val text: String? = null
                Log.i(TAG, text!!.length.toString())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вызвать NullPointerException")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val numbers = listOf(1, 2, 3)
                Log.i(TAG, numbers[5].toString())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вызвать IndexOutOfBoundsException")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                URL("https://example.com")
                    .openConnection()
                    .connect()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вызвать NetworkOnMainThreadException")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Задание 4. Работа с базой данных",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                dbHelper.addUser(
                    "Тестовый пользователь",
                    "test@gmail.com"
                )

                usersText = dbHelper.getAllUsers()
                Log.i(TAG, "Пользователь добавлен в SQLite")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить пользователя в БД")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                usersText = dbHelper.getAllUsers()
                Log.i(TAG, "Чтение пользователей из SQLite")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Показать пользователей из БД")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(usersText)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Проверка сетевого соединения",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                networkText = "Выполняется запрос..."

                Thread {
                    var connection: HttpURLConnection? = null

                    try {
                        connection = URL("https://httpbin.org/get")
                            .openConnection() as HttpURLConnection

                        connection.requestMethod = "GET"
                        connection.connectTimeout = 10000
                        connection.readTimeout = 10000

                        val responseCode = connection.responseCode

                        val response = (
                                if (responseCode in 200..299) {
                                    connection.inputStream
                                } else {
                                    connection.errorStream
                                }
                                )?.bufferedReader()?.use { it.readText() }
                            ?: "Пустой ответ сервера"

                        Log.i(TAG, "HTTP status: $responseCode")
                        Log.i(TAG, "Ответ сервера: ${response.take(300)}")

                        Handler(Looper.getMainLooper()).post {
                            networkText =
                                "Статус ответа: $responseCode\nGET-запрос выполнен"
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "Ошибка сетевого запроса", e)

                        Handler(Looper.getMainLooper()).post {
                            networkText = "Ошибка: ${e.message}"
                        }

                    } finally {
                        connection?.disconnect()
                    }
                }.start()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить HTTP GET-запрос")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(networkText)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

fun calculateTotal(price: Int, quantity: Int): Int {
    return price * quantity
}

fun isValidOrder(price: Int, quantity: Int): Boolean {
    return price >= 0 && quantity > 0
}
