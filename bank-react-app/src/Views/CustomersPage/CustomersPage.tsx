import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Loading } from "../../Components/Loading/Loading";
import { Navbar } from "../../Components/Navbar/Navbar";
import { User } from "../../Components/User/User";
import { IUser } from "../../Interfaces/IUser";
import { getAllUsers } from "../../Slices/CustomersSlice";
import { AppDispatch, RootState } from "../../Store";

export const CustomersPage: React.FC = () => {

    const userInfo = useSelector((state: RootState) => state.user);
    const customersState = useSelector((state: RootState) => state.customers);
    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    useEffect(() => {
        if (!userInfo.user) {
            navigator("/login");
        }
        else {
            console.log(userInfo);
            dispatch(getAllUsers());
        }
    }, [userInfo.users]);

    return (
        <div>
            <Navbar/>
            <div className="all-users-page">
                {customersState.users ? customersState.users.map((user: IUser) => {
                    return <User {...user} key={user.username}/>
                }) :
                <Loading/>
                }
            </div>
        </div>
    )
}