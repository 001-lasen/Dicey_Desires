package com.example.dicey_desires

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.dicey_desires.homepage.HomePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        finish()
    }
}

/*
* Assumptions Made :
   * The total of 3 chances to roll the die are counted as follows:
     * 1. The first chance is when the die are thrown initially.
     * 2. The other two are the two re-rolls that are allowed.

* Link for demo video : https://studentsiitac-my.sharepoint.com/:v:/g/personal/lasen_20220029_iit_ac_lk/EfpLW5tI2yxOunqpc_TU2H0BsW4BTFMkC9GUwDw41EwIOA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=hyjD53
 */