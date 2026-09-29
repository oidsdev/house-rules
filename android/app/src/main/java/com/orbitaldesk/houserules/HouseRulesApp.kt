package com.orbitaldesk.houserules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.orbitaldesk.houserules.data.QUESTIONS
import com.orbitaldesk.houserules.ui.screens.AboutScreen
import com.orbitaldesk.houserules.ui.screens.AgentScreen
import com.orbitaldesk.houserules.ui.screens.GuideDetailScreen
import com.orbitaldesk.houserules.ui.screens.QuizScreen
import com.orbitaldesk.houserules.ui.screens.ResultScreen
import com.orbitaldesk.houserules.ui.screens.WelcomeScreen

/**
 * Quiz state hoisted above the NavHost so answers survive navigation.
 * Everything lives in memory only — nothing is persisted or sent anywhere.
 */
class QuizState {
    var agent by mutableStateOf("other")
    val answers = mutableStateMapOf<String, String>()
    val extras = mutableStateMapOf<String, String>()

    fun reset() {
        agent = "other"
        answers.clear()
        extras.clear()
    }
}

@Composable
fun HouseRulesApp() {
    val navController = rememberNavController()
    val quiz = remember { QuizState() }

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onStart = { navController.navigate("agent") },
                onAbout = { navController.navigate("about") }
            )
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable("agent") {
            AgentScreen(
                onPick = { picked ->
                    quiz.agent = picked
                    navController.navigate("quiz/0")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "quiz/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            QuizScreen(
                index = index,
                answers = quiz.answers,
                extras = quiz.extras,
                onAnswer = { questionId, value -> quiz.answers[questionId] = value },
                onExtra = { key, value -> quiz.extras[key] = value },
                onNext = {
                    if (index < QUESTIONS.size - 1) {
                        navController.navigate("quiz/${index + 1}")
                    } else {
                        navController.navigate("result")
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("result") {
            ResultScreen(
                agent = quiz.agent,
                answers = quiz.answers,
                extras = quiz.extras,
                onOpenGuide = { guideId -> navController.navigate("guide/$guideId") },
                onRestart = {
                    quiz.reset()
                    navController.navigate("welcome") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "guide/{guideId}",
            arguments = listOf(navArgument("guideId") { type = NavType.StringType })
        ) { backStackEntry ->
            val guideId = backStackEntry.arguments?.getString("guideId") ?: ""
            GuideDetailScreen(
                guideId = guideId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
