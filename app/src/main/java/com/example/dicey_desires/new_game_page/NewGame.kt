package com.example.dicey_desires.new_game_page

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dicey_desires.new_game_page.ui.theme.Dicey_DesiresTheme
import com.example.dicey_desires.R
import com.example.dicey_desires.homepage.HomePage
import kotlin.random.Random

class NewGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dicey_DesiresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val images = listOf(
        R.drawable.die1,
        R.drawable.die2,
        R.drawable.die3,
        R.drawable.die4,
        R.drawable.die5,
        R.drawable.die6
    )
    var targetScore by remember { mutableIntStateOf(101) }
    var showTargetScoreDialog by remember { mutableStateOf(false) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var showLossDialog by remember { mutableStateOf(false) }
    var playerScore by remember { mutableIntStateOf(0) };
    var aiScore by remember { mutableIntStateOf(0) };
    var playerRerolls by remember { mutableIntStateOf(2) };
    var aiRerolls by remember { mutableIntStateOf(2) };
    var playerDie by remember { mutableStateOf(images) };
    var aiDie by remember { mutableStateOf(images) };
    var selectedDiceIndex by remember { mutableStateOf(setOf<Int>()) }
    var isRerolling by remember { mutableStateOf(false) }
    var newTurn by remember { mutableStateOf(true) }
    var threwDice by remember { mutableStateOf(false) }
    var playerScoreOfRound by remember { mutableIntStateOf(0) }
    var aiScoreOfRound by remember { mutableIntStateOf(0) }

    LaunchedEffect(playerScore, aiScore) {
        if (playerScore >= targetScore) {
            showVictoryDialog = true
        } else if (aiScore >= targetScore) {
            showLossDialog = true
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { (context as ComponentActivity).finish() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        IconButton(
            onClick = { showTargetScoreDialog = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Set Target Score"
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "A. I.",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
            )
            Text(
                text = "Rerolls Available: $aiRerolls / 2",
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                modifier = Modifier.padding(16.dp)
            ) {
                items(aiDie) { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(100.dp)
                    )
                }
            }
            Text(
                text = "Score: $aiScore / $targetScore",
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Black
            )
            SetTargetScoreDialog(
                showDialog = showTargetScoreDialog,
                onDismiss = { showTargetScoreDialog = false },
                onConfirm = { score ->
                    targetScore = score
                    showTargetScoreDialog = false
                }
            )
            ShowVictoryDialog(
                showDialog = showVictoryDialog,
                onDismiss = { showVictoryDialog = false }
            )
            ShowLossDialog(
                showDialog = showLossDialog,
                onDismiss = { showLossDialog = false }
            )
            Text(
                text = "Score: $playerScore / $targetScore",
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                modifier = Modifier.padding(16.dp)
            ) {
                items(playerDie.size) { index ->
                    Image(
                        painter = painterResource(id = playerDie[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(100.dp)
                            .border(
                                width = 2.dp,
                                color = if (isRerolling && selectedDiceIndex.contains(index))
                                    Color.Red
                                else
                                    Color.Transparent,
                                shape = RectangleShape
                            )
                            .clickable {
                                if (isRerolling) {
                                    selectedDiceIndex = if (selectedDiceIndex.contains(index)) {
                                        selectedDiceIndex - index
                                    } else {
                                        selectedDiceIndex + index
                                    }
                                }
                            }
                    )
                }
            }
            if (isRerolling) {
                Button(
                    onClick = {
                        if (playerRerolls > 0) {
                            val newPlayerDie = playerDie.toMutableList()
                            var newScore = 0
                            newPlayerDie.indices.forEach { index ->
                                if (!selectedDiceIndex.contains(index)) {
                                    val newDieValue = (1..6).random()
                                    newPlayerDie[index] = images[newDieValue - 1]
                                    newScore += newDieValue
                                } else {
                                    val currentDieValue = images.indexOf(playerDie[index]) + 1
                                    newScore += currentDieValue
                                }
                            }
                            playerDie = newPlayerDie
                            playerRerolls--
                            selectedDiceIndex = setOf()
                            playerScoreOfRound = newScore
                            if (playerRerolls == 0) {
                                isRerolling = false
                            }
                        }
                    },
                    enabled = isRerolling && playerRerolls > 0
                ) {
                    Text(text = "Confirm Reroll")
                }
            } else {
                Spacer(modifier = Modifier.height(50.dp))
            }
            Text(
                text = "Rerolls Available: $playerRerolls / 2",
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    val (playerNumbers, playerTotal) = throwDie()
                    playerDie = playerNumbers.map { images[it - 1] }
                    playerScoreOfRound = playerTotal

                    val (aiNumbers, aiTotal) = throwDie()
                    aiDie = aiNumbers.map { images[it - 1] }
                    aiScoreOfRound = aiTotal

                    newTurn = false
                    threwDice = true
                },
                    enabled = newTurn
                ) {
                    Text(text = "Throw")
                }
                Button(onClick = {
                    isRerolling = !isRerolling

                    if (isRerolling) {
                        selectedDiceIndex = setOf()
                    }
                },
                    enabled = threwDice,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRerolling)
                            Color.White
                        else
                            MaterialTheme.colorScheme.primary
                    )) {
                    Text(
                        text = if (isRerolling) "Done" else "Reroll",
                        color = if (isRerolling) Color.Black else Color.White
                    )
                }
                Button(
                    onClick = {
                        playerScore += playerScoreOfRound
                        newTurn = true
                        threwDice = false
                        playerRerolls = 2
                        aiRerolls = 2

                        // AI's Turn
                        //check if have low number dice
                        val lowScore = aiDie.count { images.indexOf(it) + 1 < 4 } > 3
                        val shouldAIReroll = shouldAIReroll()
                        if (shouldAIReroll || lowScore) {
                            while (aiRerolls > 0) {
                                selectedDiceIndex = selectDiceToKeepForAI()
                                if (selectedDiceIndex.isNotEmpty()) {
                                    val newAiDie = aiDie.toMutableList()
                                    var newScore = 0
                                    newAiDie.indices.forEach { index ->
                                        if (!selectedDiceIndex.contains(index)) {
                                            val newDieValue = (1..6).random()
                                            newAiDie[index] = images[newDieValue - 1]
                                            newScore += newDieValue
                                        } else {
                                            val currentDieValue = images.indexOf(aiDie[index]) + 1
                                            newScore += currentDieValue
                                        }
                                    }
                                    aiDie = newAiDie
                                    aiScoreOfRound = newScore
                                }
                                aiRerolls--
                            }
                        }
                        aiScore += aiScoreOfRound
                    },
                    enabled = !isRerolling && threwDice && !newTurn
                ) {
                    Text(text = "Score")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "You",
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}

fun throwDie(): Pair<List<Int>, Int> {
    val numbers = List(6) { Random.nextInt(1, 7) }
    val total = numbers.sum()
    return Pair(numbers, total)
}

fun shouldAIReroll(): Boolean {
    return (1..100).random() > 50
}

fun selectDiceToKeepForAI(): Set<Int> {
    val diceToKeep = mutableSetOf<Int>()
    val numberOfDiceToKeep = (0..5).random()
    while (diceToKeep.size < numberOfDiceToKeep) {
        val die = (0..5).random()
        if (!diceToKeep.contains(die)) {
            diceToKeep.add(die)
        }
    }
    return diceToKeep
}

@Composable
fun SetTargetScoreDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var targetScoreInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Set Target Score") },
            text = {
                Column {
                    Text(text = "Enter the target score (minimum 101):")
                    OutlinedTextField(
                        value = targetScoreInput,
                        onValueChange = {
                            targetScoreInput = it
                            isError = false
                        },
                        label = { Text("Target Score") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = isError,
                        supportingText = {
                            if (isError) {
                                Text(
                                    text = "Please enter a valid score (minimum 101)",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val targetScore = targetScoreInput.toIntOrNull()
                        if (targetScore != null && targetScore > 100) {
                            onConfirm(targetScore)
                            onDismiss()
                        } else {
                            isError = true
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ShowVictoryDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "You win!",
                    color = Color.Green
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(text = "You showed that AI.")
                        Image(
                            painter = painterResource(id = R.drawable.victory),
                            contentDescription = "Victory Image",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            },
            confirmButton = {
                val context = LocalContext.current
                Button(onClick = {
                    onDismiss()
                    val intent = Intent(context, HomePage::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Awesome!")
                }
            }
        )
    }
}

@Composable
fun ShowLossDialog (
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "You lose!",
                    color = Color.Red
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(text = "*Tsundere AI noises*")
                        Image(
                            painter = painterResource(id = R.drawable.loss),
                            contentDescription = "Loss Image",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            },
            confirmButton = {
                val context = LocalContext.current
                Button(onClick = {
                    onDismiss()
                    val intent = Intent(context, HomePage::class.java)
                    context.startActivity(intent)
                }){
                    Text("Noooo!")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Dicey_DesiresTheme {
        Content()
    }
}

@Preview(showBackground = true)
@Composable
fun SetTargetScoreDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    Dicey_DesiresTheme {
        SetTargetScoreDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { targetScore ->
                showDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowVictoryDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    Dicey_DesiresTheme {
        ShowVictoryDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowLossDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    Dicey_DesiresTheme {
        ShowLossDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false }
        )
    }
}