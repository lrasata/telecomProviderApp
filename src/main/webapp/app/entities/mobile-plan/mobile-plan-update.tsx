import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './mobile-plan.reducer';

export const MobilePlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mobilePlanEntity = useAppSelector(state => state.mobilePlan.entity);
  const loading = useAppSelector(state => state.mobilePlan.loading);
  const updating = useAppSelector(state => state.mobilePlan.updating);
  const updateSuccess = useAppSelector(state => state.mobilePlan.updateSuccess);

  const handleClose = () => {
    navigate(`/mobile-plan${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.internetDataInGB !== undefined && typeof values.internetDataInGB !== 'number') {
      values.internetDataInGB = Number(values.internetDataInGB);
    }

    const entity = {
      ...mobilePlanEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...mobilePlanEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="telecomProviderApp.mobilePlan.home.createOrEditLabel" data-cy="MobilePlanCreateUpdateHeading">
            <Translate contentKey="telecomProviderApp.mobilePlan.home.createOrEditLabel">Create or edit a MobilePlan</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="mobile-plan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('telecomProviderApp.mobilePlan.name')}
                id="mobile-plan-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('telecomProviderApp.mobilePlan.internetDataInGB')}
                id="mobile-plan-internetDataInGB"
                name="internetDataInGB"
                data-cy="internetDataInGB"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('telecomProviderApp.mobilePlan.unlimitedSmsAndCalls')}
                id="mobile-plan-unlimitedSmsAndCalls"
                name="unlimitedSmsAndCalls"
                data-cy="unlimitedSmsAndCalls"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mobile-plan" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MobilePlanUpdate;
