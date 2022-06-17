import React from "react";
import { IAccount } from "../../Interfaces/IAccount";

export const Account:React.FC<IAccount> = (account: IAccount) => {

    return(
        <div className="account">
            <div className="account-details">
                <h4>Account #{account.accountNumber}</h4>
                <h4>User: {account.user.firstname} {account.user.lastname} ({account.user.username})</h4>
                <h4>Balance: ${account.balance}</h4>
                <h4>Status: {account.status}</h4>
            </div>
        </div>
    )

}