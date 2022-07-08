import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { IAccount } from "../../Interfaces/IAccount";
import { getAccountDetails, updateAccountStatus, getAccounts } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";
import "./Account.css";

export const Account: React.FC<IAccount> = (account: IAccount) => {

    const profile = useSelector((state: RootState) => state.user);
    const accounts = useSelector((state: RootState) => state.accounts);
    const dispatch: AppDispatch = useDispatch();
    const navigator = useNavigate();

    const handleActivate = () => {
        let activate = {
            accountNumber: account.accountNumber,
            status: "ACTIVE"
        }
        dispatch(updateAccountStatus(activate));
    }

    const handleDeactivate = () => {
        let deactivate = {
            accountNumber: account.accountNumber,
            status: "DEACTIVATED"
        }
        dispatch(updateAccountStatus(deactivate));
    }

    const handleInactive = () => {
        let inactive = {
            accountNumber: account.accountNumber,
            status: "INACTIVE"
        }
        dispatch(updateAccountStatus(inactive));
    }

    const handleHistory = (event: React.MouseEvent<HTMLButtonElement>) => {
        
    }

    const handleTransaction = (event: React.MouseEvent<HTMLButtonElement>) => {
        dispatch(getAccountDetails(account.accountNumber));
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