import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../Store";

export const User: React.FC = () => {

    const userInfo = useSelector((state: RootState) => state.user);

    return (
        <div className="user">
            <h3>{userInfo.user?.firstname} {userInfo.user?.lastname}</h3>
        </div>
    )
}