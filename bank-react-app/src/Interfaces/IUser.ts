import { IAccount } from "./IAccount";
import { Type } from "./Type";

export interface IUser {
    firstname: string,
    lastname: string,
    ssn: number,
    email: string,
    username: string,
    // password: string,
    type: Type,
    accounts?: IAccount[]
}