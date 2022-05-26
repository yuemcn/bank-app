import { IUser } from "./IUser";
import { Status } from "./Status";
import { ITransaction } from "./ITransaction"

export interface IAccount {
    accountNumber: number,
    user: IUser,
    balance: number,
    status: Status,
    transactions?: ITransaction[]
}