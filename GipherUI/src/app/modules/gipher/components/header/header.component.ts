import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { MatMenuTrigger } from '@angular/material';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthenticationService, private route: Router) { }

  logout() {
    this.authService.logout();
    console.log('Header logout');
    this.route.navigate(['/Login']);
  }

  displayGifList() {
    console.log('displayGifList');
    if (!this.authService.isTokenActive()) {
      console.log('Inside if condition');
      this.route.navigate(['/Login']);
    }else{
      console.log('Inside else condition');
      this.route.navigate(['/home']);     
    }
  }

  ngOnInit() {
  }

}
