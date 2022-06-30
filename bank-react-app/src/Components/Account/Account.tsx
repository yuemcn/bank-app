import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { IAccount } from "../../Interfaces/IAccount";
import { updateAccountStatus } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";
import "./Account.css";

export const Account: React.FC<IAccount> = (account: IAccount) => {

    const profile = useSelector((state: RootState) => state.user);
    const dispatch: AppDispatch = useDispatch();
    const navigator = useNavigate();

    const handleActivate = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        let activate = {
            accountNumber: account.accountNumber,
            status: "ACTIVE"
        }
        dispatch(updateAccountStatus(activate));
        navigator("/all-accounts");
    }

    const handleDeactivate = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        let deactivate = {
            accountNumber: account.accountNumber,
            status: "DEACTIVATED"
        }
        dispatch(updateAccountStatus(deactivate));
        navigator("/all-accounts");
    }

    const handleInactive = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        let inactive = {
            accountNumber: account.accountNumber,
            status: "INACTIVE"
        }
        dispatch(updateAccountStatus(inactive));
        navigator("/all-accounts");
    }

    const handleHistory = (event: React.MouseEvent<HTMLButtonElement>) => {

    }

    const handleTransaction = (event: React.MouseEvent<HTMLButtonElement>) => {
        navigator("/transactions/new");
    }

    if (profile.user?.type.toString() === "CUSTOMER") {
        return (
            <tr>
                <td>{account.accountNumber}</td>
                <td>{account.user.username}</td>
                <td>{account.user.firstname}</td>
                <td>{account.user.lastname}</td>
                <td>${account.balance}</td>
                <td>{account.status}</td>
                <td>
                    <button onClick={handleHistory}>History</button>
                </td>
                <td>
                    <button onClick={handleTransaction}>New Transaction</button>
                </td>
            </tr>
        )    
    }
    else {
        return (
            <tr>
                <td>{account.accountNumber}</td>
                <td>{account.user.username}</td>
                <td>{account.user.firstname}</td>
                <td>{account.user.lastname}</td>
                <td>${account.balance}</td>
                <td>{account.status}</td>
                <td>
                    <button onClick={handleActivate}>Activate</button>
                </td>
                <td>
                    <button onClick={handleDeactivate}>Deactivate</button>
                </td>
                <td>
                    <button onClick={handleInactive}>Inactive</button>
                </td>
            </tr>
        )    
    }

}