import { AppPage } from './app.po';
import { browser, by, element, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display title of application', () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual('GipherUI');
  });



  it('should be able to click on Trending button to redirect to Login Page since not logged in', () => {
    browser.element(by.css('.mat-button')).click();
    browser.driver.sleep(1000);
    expect(browser.getCurrentUrl()).toContain('/Login');
    browser.driver.sleep(1000);
  });

  it('should be able to click on Favourites button to redirect to Login Page since not logged in', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.favourites-button')).click();
    expect(browser.getCurrentUrl()).toContain('/Login');
    browser.driver.sleep(1000);
  }); 

  it('should be able to click on Recommended button to redirect to Login Page since not logged in', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.recommended-button')).click();
    expect(browser.getCurrentUrl()).toContain('/Login');
    browser.driver.sleep(1000);
  });   

  it('should be able to click on Search button to redirect to Login Page since not logged in', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.search-button')).click();
    expect(browser.getCurrentUrl()).toContain('/Login');
    browser.driver.sleep(1000);
  });  

  it('should be able to click on Logout button to redirect to Login Page since not logged in', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.logout-button')).click();
    expect(browser.getCurrentUrl()).toContain('/Login');
    browser.driver.sleep(1000);
  }); 
  
  it('should be able to redirect to register page', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.registerUser')).click();
    expect(browser.getCurrentUrl()).toContain('/Register');
    browser.driver.sleep(1000);
  }); 

  it('should be able to register user', () => {
    browser.driver.sleep(1000);
    browser.element(by.id('username')).sendKeys('test');
    browser.element(by.css('.register-user')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('email')).sendKeys('test');
    browser.element(by.css('.register-user')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('password')).sendKeys('test123');
    browser.driver.sleep(1000);
    browser.element(by.css('.register-user')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('email')).sendKeys('@test.com');
    browser.driver.sleep(1000);
    browser.element(by.id('eye')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('eye')).click();
    browser.driver.sleep(1000);
    browser.element(by.css('.register-user')).click();
    browser.driver.sleep(1000);
  }); 
  
  
  it('should be able to login user', () => {
    browser.driver.sleep(1000);
    browser.element(by.id('username')).sendKeys('test');
    browser.element(by.css('.loginUser')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('password')).sendKeys('test123');
    browser.driver.sleep(1000);
    browser.element(by.id('eye')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('eye')).click();
    browser.driver.sleep(1000);    
    browser.element(by.css('.loginUser')).click();
    browser.driver.sleep(1000);
  });     
  

  it('should be able to click on Trending button to retrieve trending gifs', () => {
    browser.element(by.css('.mat-button')).click();
    browser.driver.sleep(5000);
    expect(browser.getCurrentUrl()).toContain('/home');  
    browser.driver.sleep(1000);   
  });

  it('should be able to save Trending gif to Favourites', () => {
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    const tracks = element.all(by.css('.example-card'));
    browser.element(by.css('.addbutton')).click();
    browser.driver.sleep(1000);
  });  


  it('should be able to get all data from Favourites', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.favourites-button')).click();
    browser.driver.sleep(1000);
    expect(browser.getCurrentUrl()).toContain('/Favourites');    
  });  

  it('should be able to open dialog box to update comments for a gif in Favourites', () => {
    browser.driver.sleep(500);
    const tracks = element.all(by.css('.example-card'));
    browser.driver.sleep(500);
    browser.element(by.css('.updatebutton')).click();
    browser.driver.sleep(1000);
  });  
  
  it('should be able to save updated comments for a gif in Favourites', () => {
    browser.driver.sleep(500);
    browser.element(by.css(".matInput")).sendKeys("New Comments for Update!");   
    browser.driver.sleep(500);
    browser.element(by.css('.updateComment')).click();
    browser.driver.sleep(1000);
  });    

  it('should be able to delete gif from Favourites', () => {
    browser.driver.sleep(1000);
    const tracks = element.all(by.css('.example-card'));
    browser.driver.sleep(1000);
    browser.element(by.css('.deletebutton')).click();
    browser.driver.sleep(1000);
  }); 


  it('should be able to get all data from Recommended', () => {
    browser.element(by.css('.mat-button')).click();
    browser.driver.sleep(5000);
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    const tracks = element.all(by.css('.example-card'));
    browser.element(by.css('.addbutton')).click(); 
    browser.driver.sleep(1000);
    browser.element(by.css('.recommended-button')).click();
    browser.driver.sleep(1000);
    expect(browser.getCurrentUrl()).toContain('/Recommended');    
  }); 
  
  it('should be able to open Search link', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.search-button')).click();
    browser.driver.sleep(1000);
    expect(browser.getCurrentUrl()).toContain('/Search');    
  }); 

  it('should be able to search gif', () => {
    browser.driver.sleep(1000);
    browser.element(by.id('searchInput')).sendKeys('cheeseburger');
    browser.element(by.id('search')).click();
    browser.driver.sleep(3000);
   }); 

  it('should be able to save search gif to Favourites', () => {
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    const tracks = element.all(by.css('.example-card'));
    browser.element(by.css('.addbutton')).click();
    browser.driver.sleep(1000);
  }); 

  it('should be able to logout from the application', () => {
    browser.driver.sleep(500);
    browser.element(by.css('.logout-button')).click();
    browser.driver.sleep(1000);
  }); 


});
