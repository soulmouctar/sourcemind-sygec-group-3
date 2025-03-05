export class Admin {
    uuid: string = "";
    email: string = "";
    motDePass: string = "";
    online: boolean = false;
    nonExpired:  boolean = false;
    nonLocked:  boolean = false;
    roles: string[]=[];
    enabled:  boolean = false;
    firstConnection:boolean = false;
}
