import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Account } from "../../Components/Account/Account";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IAccount } from "../../Interfaces/IAccount";
import { Status } from "../../Interfaces/Status";
import { getAllAccounts, getAllInactive } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";

import "./AllAccountsPage.css";

export const AllAccountsPage: React.FC = () => {

    const profile = useSelector((state: RootState) => state.user);
    const accounts = useSelector((state: RootState) => state.accounts);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    // let inactive: IAccount[] = [];
    let active: IAccount[] = [];
    let deactivated: IAccount[] = [];

    useEffect(() => {
        if (!profile) {
            navigator("/login");
        }
        else if (profile.user && !accounts.allAccounts) {
            dispatch(getAllAccounts());
            dispatch(getAllInactive());
            // active = filterAccounts(Status[0], accounts.allAccounts!);
            // pending = filterAccounts(Status[1], accounts.allAccounts!);
            // deactivated = filterAccounts(Status[2], accounts.allAccounts!);
        }
    }, [profile.user])
    
    // console.log(accounts.allAccounts?.length);

    // const filterAccounts = (status: string, accounts: IAccount[]) => {
    //     let result: IAccount[] = [];
    //     for (var i = 0; i < accounts.length; i++) {
    //         let account = accounts.at(i);
    //         if (account?.status.toString() == status) {
    //             result.push(account);
    //         }
    //     }
    //     return result;
    // }

    // let active: IAccount[] = filterAccounts(Status[0], accounts.allAccounts!);
    // let pending: IAccount[] = filterAccounts(Status[1], accounts.allAccounts!);
    // let deactivated: IAccount[] = filterAccounts(Status[2], accounts.allAccounts!);

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>Pending Accounts</h1>
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
                {active ? active.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>

            <div className="header">
                <h1>Deactivated Accounts</h1>
            </div>
            <table className="accounts-table">
                {deactivated ? deactivated.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>

            {/* <div className="header">
                <h1>All Accounts</h1>
            </div>
            <div className="accounts-container">
                {accounts.allAccounts ? accounts.allAccounts.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </div> */}
        </div>
    )
}