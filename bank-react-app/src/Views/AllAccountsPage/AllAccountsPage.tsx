import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Account } from "../../Components/Account/Account";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IAccount } from "../../Interfaces/IAccount";
import { getAllAccounts } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";

export const AllAccountsPage: React.FC = () => {

    const profile = useSelector((state: RootState) => state.user);
    const accounts = useSelector((state: RootState) => state.accounts);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    useEffect(() => {
        if (!profile) {
            navigator("/login");
        }
        else if (profile.user && !accounts.userAccounts) {
            dispatch(getAllAccounts());
        }
        console.log("User state: ", profile, "Accounts: ", accounts);
    }, [profile.user])

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>All Accounts</h1>
            </div>
            <div>
                {accounts.allAccounts ? accounts.allAccounts.map((account: IAccount) => {
                    return <Account {...account} key={account.accountNumber} />
                }) :
                    <Loading />
                }
            </div>
        </div>
    )
}