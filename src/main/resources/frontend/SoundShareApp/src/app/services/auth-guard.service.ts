import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{
  constructor(private router: Router,private tokenStorage: TokenStorageService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.tokenStorage.getUser();
        if(this.tokenStorage.getJwtToken()) {
          let role = currentUser.roles.find(x => x === route.data.roles);
          if(role === null){
              return false;
            }

          return true;
        }

        this.router.navigate(['/login']);
      return true;
  }
}
