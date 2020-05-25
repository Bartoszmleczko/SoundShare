import { Role } from './../role/role.model';
export class User{

    public username: string;
    public password: string;
    public firstName: string;
    public lastName: string;
    public email: string;

    constructor(username: string, password: string, firstName: string, lastName: string, email: string) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}