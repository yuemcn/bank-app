import React from "react";
import { IAccount } from "../../Interfaces/IAccount";

export const Account:React.FC<IAccount> = (account: IAccount) => {

    return(
        <div className="account">
            <div className="account-details">
                <h3>{account.accountNumber}</h3>
            </div>
        </div>
    )

}