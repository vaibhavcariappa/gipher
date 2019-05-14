import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/modules/authentication/User';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { MatSnackBar } from '@angular/material';
import { FormControl, Validators, ReactiveFormsModule} from '@angular/forms';
import { Router } from "@angular/router";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  user: User;
  emailValidate = new FormControl('', [Validators.required, Validators.email]);
  hide = true;

  constructor(
    private authService: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
    ) { 
    this.user = new User();
  }

  

  getErrorMessage() {
    return this.emailValidate.hasError('required') ? 'You must enter a value' :
        this.emailValidate.hasError('email') ? 'Not a valid email' :   '';
  }

  register() {
    console.log(this.user);
    if(!this.user.username || !this.user.email || !this.user.password){
      this.snackBar.open("Enter the Mandatory fields to Register!","",{duration: 2000});
      return;
    }
    if(this.emailValidate.hasError('email')) {
      this.snackBar.open("Enter a Valid Email Id!","",{duration: 2000});
      return;
    }
    this.authService.registerUser(this.user).subscribe(
      data => {
        if (data.status === 201) {
          this.snackBar.open("Successfully Registered!","",{duration: 2000});
          // this.authService.saveUser(this.user).subscribe(saveData => {
          //   console.log("Data saved successfully", saveData)
          // });
        }
        this.router.navigate(["/Login"]);

      },
      error => {
        console.log("error", error);
        if(error.status === 409) {
          const errorMsg = error.error.message;
          this.snackBar.open(errorMsg,"",{duration: 2000});
          
        }
      }
    );
  }

  onCancel() {
    console.log("Cancel");
    this.router.navigate(["/Login"]);
  }


  ngOnInit() {
  }

}
