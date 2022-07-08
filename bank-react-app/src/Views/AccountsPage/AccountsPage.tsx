import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { Account } from "../../Components/Account/Account";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IAccount } from "../../Interfaces/IAccount";
import { createAccount, getAccounts } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";

import "./AccountsPage.css";

export const AccountsPage: React.FC = () => {

    const profile = useSelector((state: RootState) => state.user);
    const accounts = useSelector((state: RootState) => state.accounts);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    const handleCreate = () => {
        if (!profile.user) {
            navigator("/login");
        } else {
            dispatch(createAccount(profile.user));
            dispatch(getAccounts());
            navigator("/accounts");
        }
    }

    useEffect(() => {
        if (!profile) {
            navigator("/login");
        }
        else if (profile.user && !accounts.userAccounts) {
            dispatch(getAccounts());
        }
    }, [profile.user])

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>Accounts</h1>
            </div>
            <table className="accounts-table">
                {accounts.userAccounts ? accounts.userAccounts.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </table>
            <button className="create-button" onClick={handleCreate}>Create Account</button>
        </div>
    )

}