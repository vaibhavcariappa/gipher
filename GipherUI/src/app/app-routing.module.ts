import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardContainerComponent } from 'src/app/modules/gipher/components/card-container/card-container.component';
import { FavouritesComponent } from 'src/app/modules/gipher/components/favourites/favourites.component';
import { RegisterComponent } from 'src/app/modules/authentication/components/register/register.component';
import { LoginComponent } from 'src/app/modules/authentication/components/login/login.component';
import { RecommendedComponent } from 'src/app/modules/gipher/components/recommended/recommended.component';
import { SearchComponent } from 'src/app/modules/gipher/components/search/search.component';
import { AuthGuardService } from 'src/app/modules/gipher/auth-guard.service';


const routes: Routes = [


  {
    path:"",
    component: LoginComponent,
  },
  {
    path:"Login",
    component: LoginComponent,
  },
  {
    path:"Logout",
    component: LoginComponent,
  },   
  {
    path:"Register",
    component: RegisterComponent,
  },  
  {
    path:"home",
    component: CardContainerComponent,
  },
  {
    path:"Favourites",
    component: FavouritesComponent,
    canActivate: [AuthGuardService]
  },
  {
    path:"Recommended",
    component: RecommendedComponent,
    canActivate: [AuthGuardService]
  },
  {
    path:"Search",
    component: SearchComponent,
    canActivate: [AuthGuardService]
  }    

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
