package com.example.nilpay_registration_android.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract

@Composable
fun RegistrationFormContent(
    state: RegistrationFormContract.UiState,
    pushEvent: (RegistrationFormContract.UiAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A407C),
                        Color(0xFF31CCBB)
                    )
                )
            )
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.95f)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1D32)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add New Cust",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CustomTextField(
                            value = state.fullName,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateFullName(it)) },
                            placeholder = "Full Name"
                        )
                    }
                    
                    item {
                        CustomTextField(
                            value = state.dateOfBirth,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateDateOfBirth(it)) },
                            placeholder = "Date Of Birth"
                        )
                    }
                    
                    item {
                        CustomTextField(
                            value = state.gender,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateGender(it)) },
                            placeholder = "Gender"
                        )
                    }
                    
                    item {
                        CustomTextField(
                            value = state.mobile,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateMobile(it)) },
                            placeholder = "Mobile No."
                        )
                    }
                    
                    item {
                        CustomTextField(
                            value = state.email,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateEmail(it)) },
                            placeholder = "Email"
                        )
                    }
                    
                    item {
                        CustomTextField(
                            value = state.city,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateCity(it)) },
                            placeholder = "City"
                        )
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomTextField(
                                value = state.address,
                                onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateAddress(it)) },
                                placeholder = "Address",
                                modifier = Modifier.weight(1f)
                            )
                            
                            IconButton(
                                onClick = { /* Open edit dialog */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Blue
                                )
                            }
                        }
                    }
                    
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isFamily,
                                onCheckedChange = { 
                                    pushEvent(RegistrationFormContract.UiAction.UpdateIsFamily(it))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Blue,
                                    checkmarkColor = Color.White
                                )
                            )
                            
                            Text(
                                text = "No. Family members",
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    
                    item {
                        CustomTextField(
                            value = state.nationalIdNo,
                            onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateNationalIdNo(it)) },
                            placeholder = "National ID No."
                        )
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomTextField(
                                value = state.nationalIdPhotoUri?.toString() ?: "",
                                onValueChange = { /* Read only field */ },
                                placeholder = "National ID Photo",
                                modifier = Modifier.weight(1f),
                                readOnly = true
                            )
                            
                            Button(
                                onClick = { /* Launch photo picker */ },
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Text("Upload")
                            }
                        }
                    }
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(
                        text = "Save",
                        onClick = { pushEvent(RegistrationFormContract.UiAction.SaveForm) }
                    )
                    
                    ActionButton(
                        text = "Submit",
                        onClick = { pushEvent(RegistrationFormContract.UiAction.SubmitForm) }
                    )
                    
                    ActionButton(
                        text = "Cancel",
                        onClick = { pushEvent(RegistrationFormContract.UiAction.CancelForm) }
                    )
                }
            }
        }
        
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}
