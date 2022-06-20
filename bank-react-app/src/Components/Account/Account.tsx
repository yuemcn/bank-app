import React from "react";
import { IAccount } from "../../Interfaces/IAccount";
import "./Account.css";

export const Account: React.FC<IAccount> = (account: IAccount) => {

    return (
        <button className="account">
            <h4>#{account.accountNumber}</h4>
            <br/>
            <p>User: {account.user.firstname} {account.user.lastname} ({account.user.username})</p>
            <p>Balance: ${account.balance}</p>
            <p>Status: {account.status}</p>
        </button>
    )

}