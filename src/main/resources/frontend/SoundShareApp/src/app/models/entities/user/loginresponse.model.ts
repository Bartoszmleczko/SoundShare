import { Role } from './../role/role.model';
export class LoginResponse{

    public username:string;
    public email: string;
    public roles: Role[];
    public token: string;

    constructor(username:string, email:string, roles: Role[],token: string){
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.token = token;
    }

}