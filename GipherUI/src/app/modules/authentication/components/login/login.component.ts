import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/modules/authentication/User';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { MatSnackBar } from '@angular/material';
import { Router } from "@angular/router";
export const TOKEN_NAME = "jwt_token";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  user: User;
  hide = true;

  constructor(
    private authService: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.user = new User();
   }

login() {

  if(!this.user.username || !this.user.password){
    this.snackBar.open("Enter the Mandatory fields to Login!","",{duration: 2000});
    return;
  }
  this.authService.loginUser(this.user).subscribe(
    data => {
      console.log(data);
      if (data) {
        console.log("Token value:", data.body["token"]);
        localStorage.setItem(TOKEN_NAME, data.body["token"]);
        this.snackBar.open(data.body["message"],"",{duration: 2000});
        
      } 
      this.router.navigate(["/home"]);

    },
    error => {
      console.log("error", error);
      if(error.status === 404) {
        const errorMsg = error.error.message;
        this.snackBar.open(errorMsg,"",{duration: 2000});
        
      }
    }
  );  
}

  ngOnInit() {
  }

}
