import React, { useState } from 'react';
import CurrentUserDetails from 'app/shared/components/current-user-details';
import WalletTopUpModal from 'app/shared/modal/wallet-top-up-modal';
import { WALLET_TOP_UP_OPTIONS } from 'app/shared/constants/wallet-top-up-options';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { createEntity, updateEntity } from 'app/entities/application-user/application-user.reducer';

interface Props {
  applicationUser: IApplicationUser;
}
const CurrentUserDetailsContainer = ({ applicationUser }: Props) => {
  const { phoneNumber, wallet } = applicationUser;
  const dispatch = useAppDispatch();

  const [showModal, setShowModal] = useState(false);
  const [selectedAmount, setSelectedAmount] = useState(0);

  const handleOnClickTopUp = () => {
    setShowModal(true);
  };

  const handleOnCloseModal = () => {
    setShowModal(false);
  };

  const handleOnSelectOption = (option: number) => {
    setSelectedAmount(option);
  };

  const saveEntity = () => {
    const entity = {
      ...applicationUser,
      wallet: applicationUser.wallet + selectedAmount,
    };
    dispatch(updateEntity(entity));
  };

  const handleOnConfirm = () => {
    setShowModal(false);
    saveEntity();
  };

  return (
    <>
      <CurrentUserDetails phoneNumber={phoneNumber} wallet={wallet} onButtonClick={handleOnClickTopUp} />
      <WalletTopUpModal
        showModal={showModal}
        handleClose={handleOnCloseModal}
        options={WALLET_TOP_UP_OPTIONS}
        setOptionValue={handleOnSelectOption}
        handleOnConfirm={handleOnConfirm}
      />
    </>
  );
};

export default CurrentUserDetailsContainer;
