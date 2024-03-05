import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProcessStep, NewProcessStep } from '../process-step.model';

export type PartialUpdateProcessStep = Partial<IProcessStep> & Pick<IProcessStep, 'id'>;

export type EntityResponseType = HttpResponse<IProcessStep>;
export type EntityArrayResponseType = HttpResponse<IProcessStep[]>;

@Injectable({ providedIn: 'root' })
export class ProcessStepService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/process-steps');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(processStep: NewProcessStep): Observable<EntityResponseType> {
    return this.http.post<IProcessStep>(this.resourceUrl, processStep, { observe: 'response' });
  }

  update(processStep: IProcessStep): Observable<EntityResponseType> {
    return this.http.put<IProcessStep>(`${this.resourceUrl}/${this.getProcessStepIdentifier(processStep)}`, processStep, {
      observe: 'response',
    });
  }

  partialUpdate(processStep: PartialUpdateProcessStep): Observable<EntityResponseType> {
    return this.http.patch<IProcessStep>(`${this.resourceUrl}/${this.getProcessStepIdentifier(processStep)}`, processStep, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProcessStep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProcessStep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProcessStepIdentifier(processStep: Pick<IProcessStep, 'id'>): number {
    return processStep.id;
  }

  compareProcessStep(o1: Pick<IProcessStep, 'id'> | null, o2: Pick<IProcessStep, 'id'> | null): boolean {
    return o1 && o2 ? this.getProcessStepIdentifier(o1) === this.getProcessStepIdentifier(o2) : o1 === o2;
  }

  addProcessStepToCollectionIfMissing<Type extends Pick<IProcessStep, 'id'>>(
    processStepCollection: Type[],
    ...processStepsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const processSteps: Type[] = processStepsToCheck.filter(isPresent);
    if (processSteps.length > 0) {
      const processStepCollectionIdentifiers = processStepCollection.map(
        processStepItem => this.getProcessStepIdentifier(processStepItem)!,
      );
      const processStepsToAdd = processSteps.filter(processStepItem => {
        const processStepIdentifier = this.getProcessStepIdentifier(processStepItem);
        if (processStepCollectionIdentifiers.includes(processStepIdentifier)) {
          return false;
        }
        processStepCollectionIdentifiers.push(processStepIdentifier);
        return true;
      });
      return [...processStepsToAdd, ...processStepCollection];
    }
    return processStepCollection;
  }
}
