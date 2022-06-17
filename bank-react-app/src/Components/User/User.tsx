import React from "react";
import { useSelector } from "react-redux";
import { IUser } from "../../Interfaces/IUser";
import { RootState } from "../../Store";

export const User: React.FC<IUser> = (user: IUser) => {

    return (
        <div className="user">
            <h3>{user.firstname} {user.lastname}</h3>
        </div>
    )
}