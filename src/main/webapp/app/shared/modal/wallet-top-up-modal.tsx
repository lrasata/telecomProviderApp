import React from 'react';
import { Translate } from 'react-jhipster';
import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Typography } from '@mui/material';
import Button from '@mui/material/Button';
import RadioButtonsGroup from 'app/shared/components/radio-buttons-group';

export interface IWalletTopUpModalProps {
  showModal: boolean;
  handleClose: () => void;
  options: string[] | number[];
  setOptionValue: (value: string | number) => void;
  handleOnConfirm: () => void;
}

const WalletTopUpModal = (props: IWalletTopUpModalProps) => {
  const { handleClose, options, setOptionValue, handleOnConfirm } = props;

  return (
    <Modal isOpen={props.showModal} toggle={handleClose} backdrop="static" id="top-up-wallet-modal" autoFocus={false} centered={true}>
      <ModalHeader id="top-up-wallet-title" data-cy="topUpWalletTitle" toggle={handleClose}>
        <Translate contentKey="telecomProviderApp.applicationUser.topUpModalTitle">Top up modal title</Translate>
      </ModalHeader>
      <ModalBody>
        <Typography variant="body1" gutterBottom>
          <Translate contentKey="telecomProviderApp.applicationUser.topUpModalDescription">Top up modal description</Translate>
        </Typography>
        <RadioButtonsGroup options={options} setValue={setOptionValue} />
      </ModalBody>
      <ModalFooter>
        <Button variant="outlined" onClick={handleClose} sx={{ mr: 3 }}>
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button variant="contained" onClick={handleOnConfirm}>
          <Translate contentKey="telecomProviderApp.applicationUser.topUpModalConfirmButton">Confirm</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default WalletTopUpModal;
