import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardComponent } from './components/card/card.component';
import { CardContainerComponent } from './components/card-container/card-container.component';
import { HeaderComponent } from './components/header/header.component';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { FooterComponent } from './components/footer/footer.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { RecommendedComponent } from './components/recommended/recommended.component';
import { AngularmaterialModule } from '../angularmaterial/angularmaterial.module';
import { GipherService } from 'src/app/modules/gipher/gipher.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorService } from 'src/app/modules/gipher/interceptor.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../../app-routing.module';
import { SearchComponent } from './components/search/search.component';



@NgModule({
  declarations: [
    CardComponent,
    CardContainerComponent, 
    HeaderComponent, 
    FavouritesComponent, 
    FooterComponent, 
    DialogComponent, 
    RecommendedComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AngularmaterialModule    
  ],
  exports: [
    CardContainerComponent,
    HeaderComponent,
    AppRoutingModule,
    FooterComponent,
    FavouritesComponent,
    SearchComponent

  ],
  providers: [
    GipherService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }

  ],
  entryComponents: [
    DialogComponent
  ],  
})
export class GipherModule { }
