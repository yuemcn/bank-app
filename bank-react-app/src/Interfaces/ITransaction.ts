import { IAccount } from "./IAccount";

export interface ITransaction {
    account: IAccount,
    date: Date,
    amount: number,
    description: string
}