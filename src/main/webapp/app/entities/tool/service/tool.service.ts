import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITool, NewTool } from '../tool.model';

export type PartialUpdateTool = Partial<ITool> & Pick<ITool, 'id'>;

export type EntityResponseType = HttpResponse<ITool>;
export type EntityArrayResponseType = HttpResponse<ITool[]>;

@Injectable({ providedIn: 'root' })
export class ToolService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tools');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tool: NewTool): Observable<EntityResponseType> {
    return this.http.post<ITool>(this.resourceUrl, tool, { observe: 'response' });
  }

  update(tool: ITool): Observable<EntityResponseType> {
    return this.http.put<ITool>(`${this.resourceUrl}/${this.getToolIdentifier(tool)}`, tool, { observe: 'response' });
  }

  partialUpdate(tool: PartialUpdateTool): Observable<EntityResponseType> {
    return this.http.patch<ITool>(`${this.resourceUrl}/${this.getToolIdentifier(tool)}`, tool, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITool>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITool[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getToolIdentifier(tool: Pick<ITool, 'id'>): number {
    return tool.id;
  }

  compareTool(o1: Pick<ITool, 'id'> | null, o2: Pick<ITool, 'id'> | null): boolean {
    return o1 && o2 ? this.getToolIdentifier(o1) === this.getToolIdentifier(o2) : o1 === o2;
  }

  addToolToCollectionIfMissing<Type extends Pick<ITool, 'id'>>(
    toolCollection: Type[],
    ...toolsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tools: Type[] = toolsToCheck.filter(isPresent);
    if (tools.length > 0) {
      const toolCollectionIdentifiers = toolCollection.map(toolItem => this.getToolIdentifier(toolItem)!);
      const toolsToAdd = tools.filter(toolItem => {
        const toolIdentifier = this.getToolIdentifier(toolItem);
        if (toolCollectionIdentifiers.includes(toolIdentifier)) {
          return false;
        }
        toolCollectionIdentifiers.push(toolIdentifier);
        return true;
      });
      return [...toolsToAdd, ...toolCollection];
    }
    return toolCollection;
  }
}
