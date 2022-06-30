import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Account } from "../../Components/Account/Account";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IAccount } from "../../Interfaces/IAccount";
import { Status } from "../../Interfaces/Status";
import { getAllAccounts, getAllActive, getAllDeactivated, getAllInactive } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";

import "./AllAccountsPage.css";

export const AllAccountsPage: React.FC = () => {

    const profile = useSelector((state: RootState) => state.user);
    const accounts = useSelector((state: RootState) => state.accounts);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    useEffect(() => {
        if (!profile) {
            navigator("/login");
        }
        else if (profile.user && !accounts.allAccounts) {
            dispatch(getAllAccounts());
            dispatch(getAllInactive());
            dispatch(getAllActive());
            dispatch(getAllDeactivated());
            console.log(accounts.active);
        }
    }, [profile.user])
    
    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>Inactive Accounts</h1>
            </div>
            <table className="accounts-table">
                {accounts.inactive ? accounts.inactive.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>

            <div className="header">
                <h1>Active Accounts</h1>
            </div>
            <table className="accounts-table">
                {accounts.active ? accounts.active.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>

            <div className="header">
                <h1>Deactivated Accounts</h1>
            </div>
            <table className="accounts-table">
                {accounts.deactivated ? accounts.deactivated.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>
        </div>
    )
}