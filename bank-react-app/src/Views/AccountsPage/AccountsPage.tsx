import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { Account } from "../../Components/Account/Account";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IAccount } from "../../Interfaces/IAccount";
import { getAccounts } from "../../Slices/AccountSlice";
import { AppDispatch, RootState } from "../../Store";

export const AccountPage: React.FC = () => {

    const profile = useSelector((state:RootState) => state.user);
    const accounts = useSelector((state:RootState) => state.accounts);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    useEffect(() => {
        if (!profile.user) {
            navigator("/login");
        }
        else if (profile.user && !accounts.accounts) {
            dispatch(getAccounts(profile.user.username));
        }

        console.log("User state: ", profile, "Accounts: ", accounts);
    }, [profile, accounts.accounts])

    return (
        <div>
            <Navbar />
            <br/>
            <h1>Accounts</h1>
            <br/>
            {accounts.accounts ? accounts.accounts.map((account:IAccount) => {
                return <Account {...account} key={account.accountNumber} />
            }) :
            <Loading />
            }
        </div>
    )

}